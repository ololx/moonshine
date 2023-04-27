# moonshine
This is a java utils and extensions library

[![Maven Central](https://img.shields.io/maven-central/v/io.github.ololx.moonshine/moonshine.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.ololx.moonshine%22) [![Sonatype Nexus Release](https://img.shields.io/nexus/r/io.github.ololx.moonshine/moonshine?label=Nexus%20Release&nexusVersion=2&server=https%3A%2F%2Fs01.oss.sonatype.org)](https://search.maven.org/search?q=g:%22io.github.ololx.moonshine%22)
[![Sonatype Nexus Snapshot](https://img.shields.io/nexus/s/io.github.ololx.moonshine/moonshine?label=Nexus%20Snapshot&server=https%3A%2F%2Fs01.oss.sonatype.org)](https://search.maven.org/search?q=g:%22io.github.ololx.moonshine%22) [![javadoc](https://javadoc.io/badge2/io.github.ololx.moonshine/moonshine/javadoc.svg?logo=java)](https://javadoc.io/doc/io.github.ololx.moonshine) 

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)

[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=bugs)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)
[![DeepSource](https://deepsource.io/gh/ololx/moonshine.svg/?label=active+issues&show_trend=true&token=WjXoWTUy4w1uJyI6FIFTFo46)](https://deepsource.io/gh/ololx/moonshine/?ref=repository-badge)

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=coverage)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=ololx_moonshine&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=ololx_moonshine)

# Moonshine

The Moonshine is a java library that that contains usefully utilities and tools for coding.

## Features

- The tupples collection that allow to create new tuples and iterates it.
- The stopwatch utilities that allow to get elapsed time measurements.
- The bit converting utilities that allow to code/encode Java primitives to/from byte array.
- The allocated memory meter of the selected code part in the application.
- The CPU usage meter of the selected code part in the application.

## Maven Installing

1 - Add this dependency to classpath in pom:

```xml
<dependency>
    <groupId>io.github.ololx.moonshine</groupId>
    <artifactId>${module-name}</artifactId>
    <version>${version}</version>
</dependency>
```

_Example of dependency for installing `moonshine-tuple` module with version `0.2.3` is presented bellow_

```xml
<dependency>
    <groupId>io.github.ololx.moonshine</groupId>
    <artifactId>moonshine-tuple</artifactId>
    <version>0.2.3</version>
</dependency>
```

2 - Execute this with goal

```bash
clean install
```
