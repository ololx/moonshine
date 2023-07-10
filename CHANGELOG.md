# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased] - yyyy-mm-dd

## [0.10.0] - 2023-07-09

### Added

- New method `convert()`` has been added to the Tuple collection, which allows convert
Tuple instance into other Objects.

## [0.9.0] - 2023-07-06

### Added

- New method `stream()`` has been added to the Tuple collection, which allows creating a new stream
from a Tuple object. The similar method toStream() has been deprecated and will be removed in upcoming releases.

## [0.8.2] - 2023-06-23

### Fixed

- Fixed the behavior of the byteOrder method in the ByteOrder class. Previously, an incorrect ByteIndexOperator
was returned, which was constructed for the size of bytes minus one instead of the actual size of bytes.
- Fixed the behavior of ByteIndexOperator for pdp-endian in the Endianness class. Previously, if a ByteIndexOperator was constructed
for an odd number of bytes, it could return an index out of range in the extreme positions of the array.

### Added

- New `String` coder/decoder realization.
- New `UUID` coder/decoder realization.
- New `Date` coder/decoder realization.
- New `LocalDate` coder/decoder realization.
- New `LocalDateTime` coder/decoder realization.
- New `ZonedDateTime` coder/decoder realization.
- New utility class for byte array operations, such as: `concat`, `reorder`, e.t.c.

## [0.7.0] - 2023-04-27

### Added

- New utility classes for CPU usage measuring.
- New class for CPU usage representation and converting.

## [0.6.1] - 2023-04-11

### Fixed

- The `WholeMemoryAllocatedMeter` negative result when GC activated during measuring.

## [0.6.0] - 2023-04-08

### Added

- New utility classes for allocated memory measuring.
- New class for memory representation and converting.

## [0.5.0] - 2023-03-29

### Added

- New utility class for encoding byte values to byte array.
- New utility class for decoding byte array to byte value.
- New utility class for encoding short values to byte array.
- New utility class for decoding byte array to short value.
- New utility class for encoding char values to byte array.
- New utility class for decoding byte array to char value.
- New utility class for encoding int values to byte array.
- New utility class for decoding byte array to int value.
- New utility class for encoding long values to byte array.
- New utility class for decoding byte array to long value.
- New utility class for encoding float values to byte array.
- New utility class for decoding byte array to float value.
- New utility class for encoding double values to byte array.
- New utility class for decoding byte array to double value.

## [0.4.0] - 2023-02-03

### Added

- New tuple method `toArray()`, which return an array of tuple elements
- New tuple method `toList()`, which return a list of tuple elements
- New tuple method `toSet()`, which return a set of tuple elements
- New tuple method `toStream()`, which return a stream of tuple elements
- New tuple method `contains()`, which allows to check that element contains in tuple
- New tuple methods `indexOf()` and `lastIndexOf()`, which allow to get first and last index of specified element

## [0.3.0] - 2023-01-29

### Added

- New tuple of 4 elements with the basic implementation `Quadruple`.
- New tuple of 5 elements with the basic implementation `Quintuple`.
- New tuple of 6 elements with the basic implementation `Sextuple`.
- New tuple of 7 elements with the basic implementation `Septuple`.
- New tuple of 8 elements with the basic implementation `Octuple`.
- New tuple of N elements, just interface.
- New abstraction for all tuples `AbstractTuple`.
- Basic realization of method `toString()` for all tuples.
- New Utility class for the checking index bounds.
- New Utility class for the building string of tuple.

### Changed

- Method `get()` realization for all Tuple realizations to `final`.
- Method `size()` realization for all Tuple realizations to `final`.

## [0.2.3] - 2023-01-09

### Added
- Initial public release with the following modules:
    - moonshine-bom - this is a Bill of Materials for project moonshine modules.
    - moonshine-tuple - provides data structures and utilities that can be used to create and manipulate tuples.
    - moonshine-stopwatch - provides utilities that can be used to accurately measure elapsed time.
