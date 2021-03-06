# Wordle 

### What is Wordle?
Wordle is a daily word game which asks users to guess 
a 5-letter word and gives hints to reach to the correct
guess.

Official link: https://www.powerlanguage.co.uk/wordle/

Other: https://octokatherine.github.io/word-master/
(I know it's too hard to wait 24 hours for next word)

Twitter: https://twitter.com/search?q=%23wordle

### What's this solution?
This solution is a simple java class to help solve Wordle 
puzzle online.

The users need to play the first round on Worlde UI where
they submit the first guess and get a feedback. As a next step,
users need to submit the guess and feedback shown by wordle UI
to the program. The program then suggests 10 potential words 
for next round.

```
Enter input: INDIA
Enter feedback (X -> Gray/White, Y -> yellow, G -> green): GYXXX
Suggestion(s):
ICING
ICONS
IKONS
IMINE
IMINO
IPPON
IRING
IRONE
IRONS
IRONY
```

### My guesses using the program:

1. Wordle 204 6/6 (Jan 9, 2022)

   🟨⬜⬜⬜🟨

   ⬜⬜🟩🟩🟩

   ⬜⬜🟩🟩🟩

   ⬜⬜🟩🟩🟩

   ⬜🟩🟩🟩🟩

   🟩🟩🟩🟩🟩

2. Wordle 205 4/6 (Jan 10, 2022)

   ⬜🟨⬜🟩⬜

   ⬜⬜⬜🟩🟨

   🟨⬜⬜🟩🟩
   
   🟩🟩🟩🟩🟩

3. Wordle 206 4/6 (Jan 11, 2022)

   ⬜⬜⬜🟨⬜
   
   ⬜🟨🟨⬜⬜
   
   ⬜🟩🟩⬜🟩
   
   🟩🟩🟩🟩🟩

### What's next?
1. Rank the word suggestion based on frequency

   Right now, the program suggests first 10 words which
   match the criteria based on feedback. The program is 
   more likely to show the words which appear in top of the
   word list file which is sorted alphabetically.
   
   I think frequently used words should be suggested over words
   like BIROS, CRIOS which may not be commonly used words.
