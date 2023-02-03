# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/) and this project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased] - yyyy-mm-dd

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