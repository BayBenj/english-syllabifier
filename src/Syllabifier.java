
import java.util.*;

public abstract class Syllabifier {

	public static Syllable tokenToSyllable(SyllableToken syllableToken) {
		Syllable result = new Syllable();
		for (PhonemeEnum p : syllableToken.getPhonemes()) {
			Phoneme phoneme;
			if (p.isVowel()) {
				phoneme = new VowelPhoneme(p, syllableToken.getStress());
				result.setNucleus((VowelPhoneme) phoneme);
			}
			else {
				phoneme = new ConsonantPhoneme(p);
				if (result.getNucleus() == null)
					result.getOnset().add((ConsonantPhoneme)phoneme);
				else {
					result.getCoda().add((ConsonantPhoneme)phoneme);
				}
			}
		}
		return result;
	}

	private static WordSyllables initialParse(List<VowelPhoneme> stressedPhonemes, int nSyllables) {
		WordSyllables syllables = new WordSyllables();
		Syllable currentSyllable = new Syllable();

		//Inital syllable parse
		for (int i = 0; i < stressedPhonemes.size(); i++) {
			Phoneme phoneme = stressedPhonemes.get(i);
			//if phonemeEnum is a vowel
			if (phoneme.isVowel()) {
				currentSyllable.setNucleus((VowelPhoneme) phoneme);
				if (syllables.size() < nSyllables - 1) {
					syllables.add(currentSyllable);
					currentSyllable = new Syllable();
				}
			}
			//if phonemeEnum is a consonant
			else {
				if (!currentSyllable.hasNucleus())
					currentSyllable.getOnset().add((ConsonantPhoneme) phoneme);

				else
					currentSyllable.getCoda().add((ConsonantPhoneme) phoneme);

				if (i == stressedPhonemes.size() - 1) {
					syllables.add(currentSyllable);
					break;
				}
			}
		}
		return syllables;
	}

	public static WordSyllables algorithmicallyParse(List<Phoneme> phones) {
		int nSyllables = getNSyllables(phones);
		Map<Integer,SyllablePart> map = nucleusParse(phones, nSyllables);
		onsetParse(map, phones);
		codaParse(map, phones);
		return formSyllables(map, phones);
	}

	private static int getNSyllables(List<Phoneme> phonemes) {
		int nSyllables = 0;
		for (Phoneme phoneme : phonemes) {
			if (phoneme.phonemeEnum == null) {
//                System.out.println("stop for test");
			}
			if (phoneme.phonemeEnum.isVowel())
				nSyllables++;
		}
		return nSyllables;
	}

	/**
	 * Labels the nuclei, first onset, and last coda of the word.
	 */
	private static Map<Integer,SyllablePart> nucleusParse(List<Phoneme> phonemes, int nSyllables) {
		Map<Integer,SyllablePart> wordMap = new HashMap<>();
		int nucleusIndex = 0;
		for (int i = 0; i < phonemes.size(); i++) {
			//if phonemeEnum is a vowel
			if (phonemes.get(i).phonemeEnum.isVowel()) {
				wordMap.put(i,SyllablePart.NUCLEUS);
				nucleusIndex++;
			}
			//if phonemeEnum is a consonant and comes before the first nucleus
			else if (nucleusIndex == 0) {
				wordMap.put(i,SyllablePart.ONSET);
			}
			//if phonemeEnum is a consonant and comes after the last nucleus
			else if (nucleusIndex == nSyllables) {
				wordMap.put(i,SyllablePart.CODA);
			}
		}
		return wordMap;
	}

	/**
	 * Builds the longest possible onset for each syllable, according to 13 phonotactic rules of English.
	 */
	private static Map<Integer,SyllablePart> onsetParse(Map<Integer,SyllablePart> wordMap,
														List<Phoneme> phonemes) {
		//Build nuclei map
		Map<Integer,SyllablePart> nuclei = new HashMap<>();
		for (Map.Entry<Integer,SyllablePart> entry : wordMap.entrySet()) {
			if (entry.getValue() == SyllablePart.NUCLEUS) {
				nuclei.put(entry.getKey(), entry.getValue());
			}
		}

		for (Map.Entry<Integer,SyllablePart> entry : nuclei.entrySet()) {
			int i = entry.getKey() - 1;
			if (i >= 0) {
				List<PhonemeEnum> candidateOnset = new ArrayList<>();
				candidateOnset.add(phonemes.get(i).phonemeEnum);
				while (!wordMap.keySet().contains(i) && followsOnsetRules(candidateOnset)) {
					wordMap.putIfAbsent(i, SyllablePart.ONSET);
					i--;
					if (i >= 0 && i < phonemes.size())
						candidateOnset.add(0, phonemes.get(i).phonemeEnum);
					else
						break;
				}
			}
		}
		return wordMap;
	}

	/**
	 * Labels all empty indexes as coda. //TODO this may need to be upgraded
	 */
	private static Map<Integer,SyllablePart> codaParse(Map<Integer,SyllablePart> wordMap,
													   List<Phoneme> phonemes) {
		//Build nuclei map
		Map<Integer,SyllablePart> nuclei = new HashMap<>();
		for (Map.Entry<Integer,SyllablePart> entry : wordMap.entrySet()) {
			if (entry.getValue() == SyllablePart.NUCLEUS) {
				nuclei.put(entry.getKey(), entry.getValue());
			}
		}

		//Label all empty indexes as coda
		for (int i = 0; i < phonemes.size(); i++) {
			if (!wordMap.containsKey(i)) {
				wordMap.putIfAbsent(i, SyllablePart.CODA);
			}
		}

		//Build a nucleusNum->(coda,onset) map
		TreeMap<Integer,SyllablePart> treeMap = new TreeMap<>(wordMap);
		Map<Integer, Pair<List<PhonemeEnum>,List<PhonemeEnum>>> codas_onsets = new HashMap<>();
		List<PhonemeEnum> currentCoda = new ArrayList<>();
		List<PhonemeEnum> currentOnset = new ArrayList<>();
		int nucleusNum = -1;
		for (Map.Entry<Integer,SyllablePart> entry : treeMap.entrySet()) {
			if (entry.getValue() == SyllablePart.ONSET) {
				if (nucleusNum > -1) {
					currentOnset.add(phonemes.get(entry.getKey()).phonemeEnum);
				}
			}
			else if (entry.getValue() == SyllablePart.NUCLEUS) {
				nucleusNum++;
				if (nucleusNum != 0) {
					codas_onsets.put(entry.getKey(),new Pair<>(currentCoda, currentOnset));
					currentCoda = new ArrayList<>();
					currentOnset = new ArrayList<>();
				}
			}
			else if (entry.getValue() == SyllablePart.CODA) {
				if (nucleusNum < nuclei.size() - 1) {
					currentCoda.add(phonemes.get(entry.getKey()).phonemeEnum);
				}
			}
		}

		//For each (coda,onset) pair, ensure coda passes rules. If not, take phonemes from onset until both coda and onset pass rules, or until there is no possible match.
		for (Map.Entry<Integer, Pair<List<PhonemeEnum>,List<PhonemeEnum>>> entry : codas_onsets.entrySet()) {
			if (!followsCodaRules(entry.getValue().getFirst())) {
				List<PhonemeEnum> candidateCoda = new ArrayList<>(entry.getValue().getFirst());
				List<PhonemeEnum> candidateOnset = new ArrayList<>(entry.getValue().getSecond());
				while (!candidateOnset.isEmpty()) {
					//adding to coda, taking from onset
					PhonemeEnum switched = candidateOnset.remove(0);
					candidateCoda.add(switched);
					if (followsCodaRules(candidateCoda) && followsOnsetRules(candidateOnset)) {
						codas_onsets.put(entry.getKey(),new Pair<>(candidateCoda,candidateOnset));

						//update wordMap
						for (PhonemeEnum p : entry.getValue().getFirst()) {
							for (Map.Entry<Integer,SyllablePart> entry2 : nuclei.entrySet()) {
								if (entry2.getKey() == entry.getKey()) {
									int index = 0;
									for (PhonemeEnum codaPhonemeEnum : entry.getValue().getFirst()) {
										wordMap.put(index + entry.getKey(), SyllablePart.CODA);
										index++;
									}
									for (PhonemeEnum onsetPhonemeEnum : entry.getValue().getSecond()) {
										wordMap.put(index + entry.getKey(), SyllablePart.ONSET);
										index++;
									}
								}
							}
						}
						break;
					}
				}
			}
		}
		return wordMap;
	}

	private static boolean followsOnsetRules(List<PhonemeEnum> candidateOnset) {
		if (candidateOnset != null && candidateOnset.size() > 0) {
			//non-complex
			boolean passing = onsetRule1(candidateOnset);
			if (!passing)
				return false;

			//non-complex
			passing = onsetRule2(candidateOnset);
			if (!passing)
				return false;

			//non-complex?
			passing = onsetRule3(candidateOnset);
			if (!passing)
				return false;

			//Rules for complex onsets
			if (candidateOnset.size() > 1) {
				passing = onsetRule4(candidateOnset);
				if (!passing)
					return false;

				passing = onsetRule5(candidateOnset);
				if (!passing)
					return false;

				passing = onsetRule6(candidateOnset);
				if (!passing)
					return false;

				passing = onsetRule7(candidateOnset);
				if (!passing)
					return false;

				passing = onsetRule8(candidateOnset);
				if (!passing)
					return false;

				passing = onsetRule9(candidateOnset);
				if (!passing)
					return false;
			}
		}
		return true;
	}

	private static boolean onsetRule1(List<PhonemeEnum> onset) {
		//The onset contains only consonants
		for (PhonemeEnum phonemeEnum : onset)
			if (phonemeEnum.isVowel())
				return false;
		return true;
	}

	private static boolean onsetRule2(List<PhonemeEnum> onset) {
		//No NG in onset
		if (onset.contains(PhonemeEnum.NG))
			return false;
		return true;
	}

	private static boolean onsetRule3(List<PhonemeEnum> onset) {
		//Non-alveolar nasals in onset (M, NG) must be homorganic with the next phonemeEnum TODO next phonemeEnum in onset or in the word???
		if (onset != null && onset.size() > 1) {
			for (int i = 0; i < onset.size(); i++) {
				PhonemeEnum phonemeEnum = onset.get(i);
				if ((phonemeEnum == PhonemeEnum.M || phonemeEnum == PhonemeEnum.NG) && i < onset.size() - 1) {
					if (PhonemeEnum.getPlace(phonemeEnum) != PhonemeEnum.getPlace(onset.get(i + 1))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static boolean onsetRule4(List<PhonemeEnum> onset) {
		//No adjacent, duplicate phonemes in the onset.
		for (int i = 0; i < onset.size(); i++) {
			if (    (i > 0 && onset.get(i) == onset.get(i - 1)) ||
					(i < onset.size() - 1 && onset.get(i) == onset.get(i + 1)))
				return false;
		}
		return true;
	}

	private static boolean onsetRule5(List<PhonemeEnum> onset) {
		//No affricates in complex onsets
		for (PhonemeEnum phonemeEnum : onset)
			if (PhonemeEnum.getManner(phonemeEnum) == MannerOfArticulation.AFFRICATE)
				return false;
		return true;
	}

	private static boolean onsetRule6(List<PhonemeEnum> onset) {
		//The first consonant in a complex onset must be an obstruent
		if (onset.get(0) == PhonemeEnum.HH) {   //TODO this is an exception I made for "ex-hume"
			return true;
		}
		if (!onset.get(0).isObstruent())
			return false;
		return true;
	}

	private static boolean onsetRule7(List<PhonemeEnum> onset) {
		//The second consonant in a complex onset must not be a voiced obstruent
		if (onset.get(1).isObstruent() && onset.get(1).isVoiced())
			return false;
		return true;
	}

	private static boolean onsetRule8(List<PhonemeEnum> onset) {
		//If the first consonant in a complex onset is not an /s/, the second must be a liquid or a semivowel
		if (    onset.get(0) != PhonemeEnum.S &&
				PhonemeEnum.getManner(onset.get(1)) != MannerOfArticulation.LIQUID &&
				PhonemeEnum.getManner(onset.get(1)) != MannerOfArticulation.SEMIVOWEL)
			return false;
		return true;
	}

	private static boolean onsetRule9(List<PhonemeEnum> onset) {
		//No nasal in first position of a complex onset
		if (PhonemeEnum.getManner(onset.get(0)) == MannerOfArticulation.NASAL)
			return false;
		return true;
	}

	private static boolean followsCodaRules(List<PhonemeEnum> candidateCoda) {
		if (candidateCoda != null && candidateCoda.size() > 0) {
			//non-complex
			boolean passing = codaRule1(candidateCoda);
			if (!passing)
				return false;

			//non-complex
			passing = codaRule2(candidateCoda);
			if (!passing)
				return false;

			//non-complex
			passing = codaRule3(candidateCoda);
			if (!passing)
				return false;

			//Rules for complex coda
			if (candidateCoda.size() > 1) {
				passing = codaRule4(candidateCoda);
				if (!passing)
					return false;

				passing = codaRule5(candidateCoda);
				if (!passing)
					return false;

				passing = codaRule6(candidateCoda);
				if (!passing)
					return false;

				passing = codaRule7(candidateCoda);
				if (!passing)
					return false;

				passing = codaRule8(candidateCoda);
				if (!passing)
					return false;
			}
		}
		return true;
	}

	private static boolean codaRule1(List<PhonemeEnum> coda) {
		//The coda contains only consonants
		for (PhonemeEnum phonemeEnum : coda)
			if (phonemeEnum.isVowel())
				return false;
		return true;
	}

	private static boolean codaRule2(List<PhonemeEnum> coda) {
		//No HH in coda
		if (coda.contains(PhonemeEnum.HH))
			return false;
		return true;
	}

	private static boolean codaRule3(List<PhonemeEnum> coda) {
		//No semivowels in coda
		for (PhonemeEnum phonemeEnum : coda)
			if (PhonemeEnum.getManner(phonemeEnum) == MannerOfArticulation.SEMIVOWEL)
				return false;
		return true;
	}

	private static boolean codaRule4(List<PhonemeEnum> coda) {
		//No adjacent, duplicate phonemes in the coda.
		for (int i = 0; i < coda.size(); i++)
			if (    (i > 0 && coda.get(i) == coda.get(i - 1)) ||
					(i < coda.size() - 1 && coda.get(i) == coda.get(i + 1)))
				return false;
		return true;
	}

	private static boolean codaRule5(List<PhonemeEnum> coda) {
		//If there is a complex coda, the second consonant must not be /ŋ/, /ʒ/, or /ð/
		if (coda.get(1) == PhonemeEnum.NG || coda.get(1) == PhonemeEnum.ZH || coda.get(1) == PhonemeEnum.DH)
			return false;
		return true;
	}

	private static boolean codaRule6(List<PhonemeEnum> coda) {
		//If the second consonant in a complex coda is voiced, so is the first
		if (coda.get(1).isVoiced() && !coda.get(0).isVoiced())
			return false;
		return true;
	}

	private static boolean codaRule7(List<PhonemeEnum> coda) {
		//Two obstruents in the oldWordSpecific coda must share voicing
		PhonemeEnum obstruent1 = null;
		PhonemeEnum obstruent2 = null;
		for (PhonemeEnum phonemeEnum : coda) {
			if (phonemeEnum.isObstruent()) {
				if (obstruent1 == null) {
					obstruent1 = phonemeEnum;
				}
				else {
					obstruent2 = phonemeEnum;
					break;
				}
			}
		}
		if (obstruent1 != null && obstruent2 != null && obstruent1.isVoiced() != obstruent2.isVoiced()) {
			return false;
		}
		return true;
	}

	private static boolean codaRule8(List<PhonemeEnum> coda) {
		//Non-alveolar nasals (M, NG) must be homorganic with the next phonemeEnum
		for (int i = 0; i < coda.size(); i++) {
			PhonemeEnum phonemeEnum = coda.get(i);
			if ((phonemeEnum == PhonemeEnum.M || phonemeEnum == PhonemeEnum.NG) && i < coda.size() - 1) {
				if (PhonemeEnum.getPlace(phonemeEnum) != PhonemeEnum.getPlace(coda.get(i + 1))) {
					return false;
				}
			}
		}
		return true;
	}

	private static WordSyllables formSyllables(Map<Integer,SyllablePart> wordMap, List<Phoneme> phonemes) {
		WordSyllables result = new WordSyllables();
		Syllable currentSyllable = new Syllable();
		SyllablePart lastPart = null;
		int i = 0;
		for (Map.Entry<Integer,SyllablePart> entry : wordMap.entrySet()) {
			if (entry.getValue() == SyllablePart.ONSET) {
				if (lastPart == SyllablePart.CODA || lastPart == SyllablePart.NUCLEUS){
					result.add(currentSyllable);
					currentSyllable = new Syllable();
				}
				currentSyllable.getOnset().add((ConsonantPhoneme) phonemes.get(i));
				lastPart = SyllablePart.ONSET;
			}
			else if (entry.getValue() == SyllablePart.NUCLEUS) {
				if (lastPart == SyllablePart.CODA || lastPart == SyllablePart.NUCLEUS){
					result.add(currentSyllable);
					currentSyllable = new Syllable();
				}
				currentSyllable.setNucleus((VowelPhoneme) phonemes.get(i));
				lastPart = SyllablePart.NUCLEUS;
			}
			else if (entry.getValue() == SyllablePart.CODA) {
				currentSyllable.getCoda().add((ConsonantPhoneme) phonemes.get(i));
				lastPart = SyllablePart.CODA;
			}
			i++;
		}
		if (lastPart == SyllablePart.CODA || lastPart == SyllablePart.NUCLEUS){
			result.add(currentSyllable);
		}
		return result;
	}

}







/*
14 phonotactic constaints of English:
    @All syllables have a nucleus
    @No geminates (2 duplicate phonemes in a row. None of these in the onset or coda )
    @No onset /ŋ/ (NG)
    @No /h/ (HH) in the syllable coda
    @No affricates in complex onsets (more than 1 phonemeEnum)
    @The first consonant in a complex onset must be a stop
    @The second consonant in a complex onset must not be a voiced stop
    @If the first consonant in a complex onset is not an /s/, the second must be a liquid or a semivowel
    #Every subsequence contained within a sequence of consonants must obey all the relevant phonotactic rules (the substring principle)[5]
    @No semivowels (semivowels) in codas
    @If there is a complex coda, the second consonant must not be /ŋ/, /ʒ/, or /ð/
    @If the second consonant in a complex coda is voiced, so is the first
    @Non-alveolar nasals (M, NG) must be homorganic with the next phonemeEnum
        M - P, B
        NG - G, K
    @Two stops in the oldWordSpecific coda must share voicing
 */


//sonnarent = non vowel that you can hold out: nasals, liquids
