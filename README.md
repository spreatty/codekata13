# Codekata13

## About
This is a solution for http://codekata.com/kata/kata13-counting-code-lines/

## Building and running
The project requires Maven 3.6.0+ and JDK 11+ for building.\
\
To build project use following command: `mvn clean package`.\
This will clean previous build if exists, run unit tests, and create executable *.jar file.\
\
Then to run the compiled program type: `java -jar target/*.jar file-or-directory-name`.

## Solution details
The implementation considers that input source is a source with valid comments and literals usage.
The following examples are considered impossible:
1. `String s = "text` or `char c = 'c` - unclosed literal
2. `/* comment */ */` or `String s = "/*";*/` - unopened multi-line comment
2. `String s = \"text"` - unopened literal, also can not escape character outside of literal

During implementation, I learned that java compiler understands unicode escapes and translates
them during compiling, and this became part of these cases that I came up with:
#### A simple source
A plain source where comments and literals do not overlap each other. This is the simplest case.
Example:
```
1   static void main(String[] args) {
2       String s = /* comment */ "string";
3       char c = 'a'; // a
4   }
```

#### Literals and comments may overlap each other
If literal starts inside comment, it is not actual literal. This also works in opposite direction
\- i.e. if comment starts inside literal, it is not actual comment. In the following example I am
going to show how string "cancels" comment and thus prevents lines from discarding:
```
1   static void main(String[] args) {
2       String s = "I cancel /* comment, so that next line counts";
3       char c = '"'; // This line does not open string literal because it is inside character literal */
4   }
```
To process overlapping properly we need to detect literal bound correctly, and that is how I came
up with the next case.

#### Literals may contain escaped characters that represent their bounds
I mean these guys: `\"`, `\'`. The problem here is that can not simply find closing sequence
(i.e. `"`), but also consider the context. Additionally, string literal may contain sequence like
this `\\\\\\\"`, it is even hard to tell whether it is escaped. Our example becomes more and more
complicated:
```
1   static void main(String[] args) {
2       String s = "I do not cancel\\"/* this comment, because string already ended";
-       char c = '\''; // Need to handle single quote escape as well */
3   }
```

#### Unicode escape may appear
Not only in literals, but anywhere in the file and that will work. This `\u0022string\u0022` is a
valid java string. The same way we may type any character, which means we need to unescape
these tokens before working with the source. Let's add some unicode to previous example:
```
1   static void\u0020main(String[] args) {
2       St\u0072ing s = \u0022I do not cancel\\"/* this comment, because string already ended";
-       char c = '\u005c''; // Need to handle single quote escape as well */
3   }
```