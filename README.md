# TP Bowling

## Object Hierarchy

:: 11 Objects total ::

- 4 test files
  - 3 test classes
  - 1 interface (a testing utility)

- 4 exception classes
  - 3 distinct exceptions
  - 1 inherited exception

- 3 functional objects
  - 2 classes (Game & Frame)
  - 1 nested enum (Frame.FrameType)

## Notable elements

- Tried to include some JavaDoc where relevant, on the more obscure functions.
- I decided not to inherit from `RuntimeException`. This required a minor restructuring of my tests classes to avoid bloating the code with `try/catch` statements everywhere.

## Future

- More to come?

# Clean Code

See file `mauvais_code.java` in root directory.