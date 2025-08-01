#!/bin/bash
set -e  # Exit immediately if any command fails


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

# Build with Maven
./mvnw clean package -DskipTests -Dmaven.test.skip=true

# Verify JAR was created (fixed check)
if ! ls target/*.jar 1> /dev/null 2>&1; then
  echo "Error: No JAR file created in target directory!"
  ls -l target/
  exit 1
else
  echo "JAR file successfully created:"
  ls -l target/*.jar
fi
EOF

chmod +x run.sh

./run.sh

echo "Build completed successfully"