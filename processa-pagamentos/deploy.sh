#!/bin/bash
set -e


if [ ! -f ".env" ]; then
  echo "Error: .env file not found!"
  exit 1
fi

export $(grep -v '^#' .env | xargs)

cat << 'EOF' > run.sh
#!/bin/bash
set -e

# Load environment variables if .env exists
if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
fi

# Build with Gradle
exec ./gradlew clean bootJar

# Verify JAR was created (fixed check)
if ! ls build/libs/*.jar 1> /dev/null 2>&1; then
  echo "Error: No JAR file created in build directory!"
  ls -l build/libs/
  exit 1
else
  echo "JAR file successfully created:"
  ls -l build/libs/*.jar
fi
EOF

chmod +x run.sh

./run.sh