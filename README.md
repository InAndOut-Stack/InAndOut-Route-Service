# InAndOut-Route-Service

## Setup

This project uses git submodules for API modelling. To ensure the project builds correctly, initialize the submodules when cloning:

```bash
git clone --recurse-submodules <repo-url>
```

Alternatively, if you've already cloned the repository, run:

```bash
git submodule update --init --recursive
```

## Build

To build the project, use the Gradle wrapper:

```bash
./gradlew build
```

It is recommended to install the [Smithy CLI](https://smithy.io/2.0/guides/smithy-cli/cli_installation.html) for diverse model checks and validation.
