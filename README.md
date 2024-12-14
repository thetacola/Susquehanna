# Susquehanna

Susquehanna is an all-in-one conlang manager.

## Installation

Susquehanna pre-built binaries are included with each release. They simply need to be run. For compiling, use `mvn clean install` inside the project's directory.

## Usage

Please see the attached manual for details on how to use the program. The manual can be found inside the 'manual' folder, and is called 'manual.pdf'.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to edit version information and change the manual for any changes made.

### Build versioning
Development builds follow the pattern of {last two digits of year}w{week number}{letter of build}. For example, the 8th build made in the 50th week of 2024 would be "24w50h".
If there has been more than 26 builds in one week, letters switch to Greek. For example, the 28th build would be "24w50β". This continues through the Unicode list of scripts, skipping any extensions, diacritics, non-letters, or supplements.
If the end of the Hangul Syllables table has been reached in build names, perhaps it would be a good idea to take a break for a few days.

Release builds follow semantic versioning, with the first number changing when a backwards-incompatible change is made, the second number changing with any new functionality or API change, and the third number changing for all other updates.

## License

[Apache 2.0](https://choosealicense.com/licenses/apache-2.0/) 
