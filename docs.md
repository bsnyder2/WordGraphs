Let s = startWord, e = endWord.  
If s == e, a path can always be found.  
If s != e and the input conditions are valid, a path may be found, but it is not guaranteed.  
If s != e and the input conditions are invalid, no path can be found.

P represents valid input conditions.  
\- represents invalid input conditions.

|                  | len(s) == len(e)* | len(s) < len(e) | len(s) > len(e) |
|------------------|-------------------|-----------------|-----------------|
| No operations    | -                 | -               | -               |
| Set              | P                 | -               | -               |
| Add              | -                 | P               | -               |
| Remove           | -                 | -               | P               |
| Set, add         | P                 | P               | -               |
| Set, remove      | P                 | -               | P               |
| Add, remove      | P                 | P               | P               |
| Set, add, remove | P                 | P               | P               |

\* excluding s == e
