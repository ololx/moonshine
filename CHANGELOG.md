# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/) and this project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased] - yyyy-mm-dd

## [0.3.0] - 2023-01-27

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

### Changed

- Method `get()` realization for all Tuple realizations to `final`.
- Method `size()` realization for all Tuple realizations to `final`.

## [0.2.3] - 2023-01-09

### Added
- Initial public release with the following modules:
    - moonshine-bom - this is a Bill of Materials for project moonshine modules.
    - moonshine-tuple - provides data structures and utilities that can be used to create and manipulate tuples.
    - moonshine-stopwatch - provides utilities that can be used to accurately measure elapsed time.