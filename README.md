# english-syllabifier

Don't want to use a dictionary for syllable data? Neither did I! A syllabifier allows you to parse phonemes into syllables simply by following a language's phonotactic rules.

The English language has [14 phonotactic rules] (https://en.wikipedia.org/wiki/Phonotactics). This syllable parser uses the fourteen phonotactic rules of English to syllabify sequences of phonemes. 

The algorithm works by:
1. labelling a word’s nuclei ν
2. prepending onset phonemes ω to each nucleus ν while nophonotactic rule is broken
3. appending remaining coda phonemes κ to each nucleus ν while no phonotactic rule is broken

Certain words that violate these rules (for example, words of non-English origin) may be syllabified incorrectly.

## Syllable composition
A word is a sequence of syllables. A syllable is made of an onset ω, nucleus ν, and coda κ. The nucleus is the central vowel phoneme. The onset is the consonant phoneme(s) preceding the nucleus. The coda is the consonant phoneme(s) following the nucleus. Both the onset and coda may be empty.

![alt text](https://github.com/BayBenj/english-syllabifier/syllable-parts.jpg "Syllable Parts")
