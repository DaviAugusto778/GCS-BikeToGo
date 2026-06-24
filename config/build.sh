#!/usr/bin/env bash
# =============================================================================
# CFG-01 — Script de build reprodutível do BikeToGo
# IC empacotado: COD-02 (back-end / app Java Swing, pacote `pooTrabalhoFinal`)
# Artefato gerado: BLD-01 — build/biketogo-<versão>.jar (JAR executável)
# Norma base: IEEE Std 828-2012 (PGCS / DOC-04)
#
# OBJETIVO: produzir um artefato BYTE-A-BYTE REPRODUTÍVEL. As decisões de
# determinismo estão comentadas inline com a etiqueta [DET].
#
# Uso:
#   ./config/build.sh            # compila e empacota o JAR
#   ./config/build.sh --verify   # compila duas vezes e compara o SHA-256
#
# Variáveis de ambiente:
#   JAVA_HOME           caminho do JDK (default: derivado de `java`)
#   SOURCE_DATE_EPOCH   timestamp Unix p/ as entradas do JAR (default: baseline)
# =============================================================================
set -euo pipefail

# --- Parâmetros da baseline -------------------------------------------------
APP_VERSION="0.3.0"                       # SemVer — ver relatório técnico §SemVer
MAIN_CLASS="pooTrabalhoFinal.Launcher"
JAVA_RELEASE="21"                         # [DET] versão de bytecode fixa (--release)
# [DET] Timestamp fixo das entradas do JAR. Sem isto, o `jar` grava a hora atual
#       e o artefato muda a cada build. 1782259200 = 2026-06-24T00:00:00Z.
: "${SOURCE_DATE_EPOCH:=1782259200}"

# --- Localização (independe do diretório de invocação) ----------------------
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
SRC_DIR="$ROOT_DIR/src/backend/java"
BUILD_DIR="$ROOT_DIR/build"
CLASSES_DIR="$BUILD_DIR/classes"

# --- Toolchain (contorna o shim do Windows que não expõe `jar`) -------------
if [ -z "${JAVA_HOME:-}" ]; then
  JAVA_HOME="$(java -XshowSettings:properties 2>&1 | sed -n 's/^[[:space:]]*java.home = //p')"
fi
if command -v cygpath >/dev/null 2>&1; then JAVA_HOME="$(cygpath -u "$JAVA_HOME")"; fi
JAVAC="$JAVA_HOME/bin/javac"
JAR="$JAVA_HOME/bin/jar"
for t in "$JAVAC" "$JAR"; do
  [ -x "$t" ] || [ -x "$t.exe" ] || { echo "ERRO: ferramenta não encontrada: $t" >&2; exit 1; }
done
JAVAC_VER="$("$JAVAC" -version 2>&1 | awk '{print $2}')"
echo "» JDK:            $JAVAC_VER  ($JAVA_HOME)"
case "$JAVAC_VER" in
  21.*) : ;;
  *) echo "  AVISO: baseline fixada em JDK 21; build com $JAVAC_VER pode alterar o hash (ver relatório §Reprodutibilidade)." >&2 ;;
esac

# --- ISO do timestamp p/ o jar ----------------------------------------------
JAR_DATE="$(date -u -d "@$SOURCE_DATE_EPOCH" +%Y-%m-%dT%H:%M:%SZ 2>/dev/null \
            || date -u -r "$SOURCE_DATE_EPOCH" +%Y-%m-%dT%H:%M:%SZ)"

build_jar () {
  local out_dir="$1"
  local classes="$out_dir/classes"
  rm -rf "$out_dir"; mkdir -p "$classes"

  # [DET] Compila a lista de fontes em ordem estável (LC_ALL=C) e encoding fixo.
  #       Bytecode do javac não contém timestamps -> determinístico p/ um mesmo JDK.
  local sources
  sources="$(find "$SRC_DIR" -name '*.java' | LC_ALL=C sort)"
  # shellcheck disable=SC2086
  "$JAVAC" --release "$JAVA_RELEASE" -encoding UTF-8 -d "$classes" $sources

  # Recursos (imagens) carregados via getClass().getResource("...") -> caminho do pacote
  local pkgdir="$classes/pooTrabalhoFinal"
  for img in "$SRC_DIR"/*.png "$SRC_DIR"/*.jpg; do
    [ -e "$img" ] && cp "$img" "$pkgdir/"
  done

  # [DET] Empacota com entradas em ordem estável e timestamp fixo (--date).
  local jarfile="$out_dir/biketogo-$APP_VERSION.jar"
  ( cd "$classes" && "$JAR" --create --file "$jarfile" \
        --main-class "$MAIN_CLASS" --date="$JAR_DATE" \
        $(find . -type f | LC_ALL=C sort | sed 's|^\./||') )
  echo "$jarfile"
}

echo "» Versão (SemVer): $APP_VERSION"
echo "» Timestamp (jar): $JAR_DATE  (SOURCE_DATE_EPOCH=$SOURCE_DATE_EPOCH)"
echo "» Compilando e empacotando…"
JARFILE="$(build_jar "$BUILD_DIR")"
SHA="$(sha256sum "$JARFILE" | awk '{print $1}')"
printf '%s  %s\n' "$SHA" "build/biketogo-$APP_VERSION.jar" > "$BUILD_DIR/SHA256SUMS-build.txt"
echo "» Artefato:        build/biketogo-$APP_VERSION.jar"
echo "» SHA-256:         $SHA"

if [ "${1:-}" = "--verify" ]; then
  echo "» --verify: segundo build independente para conferir reprodutibilidade…"
  JAR2="$(build_jar "$BUILD_DIR/.verify")"
  SHA2="$(sha256sum "$JAR2" | awk '{print $1}')"
  rm -rf "$BUILD_DIR/.verify"
  if [ "$SHA" = "$SHA2" ]; then
    echo "✓ REPRODUTÍVEL: os dois builds são byte-a-byte idênticos ($SHA)."
  else
    echo "✗ NÃO reprodutível: $SHA  !=  $SHA2" >&2
    exit 1
  fi
fi
