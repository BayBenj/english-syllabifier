//This is the Arpabet phoneme tagset, used by the CMU phonetics.Pronunciation Dictionary
public enum PhonemeEnum {
	/* 15 Vowels
		phonetics.Height - low, mid, high
		phonetics.Frontness - front, central, back
	*/
	AA,	//			odd         AA D
	AE,	//			at			AE T
	AH,	//			hut			HH AH T
	AO,	//			ought		AO T
	AW,	//			cow			K AW
	AY,	//			hide		HH AY D
	EH,	//			Ed			EH D
	ER,	//			hurt		HH ER T
	EY,	//			ate			EY T
	IH,	//			it			IH T
	IY,	//			eat			IY T
	OW,	//			oat			OW T
	OY,	//			toy			T OY
	UH,	//			hood		HH UH D
	UW,	//			two			T UW

	/* 24 Consonants
		Manner of Articulation
		Place of Articulation
		Voicing
	*/
	B,	//			be			B IY
	CH,	//			cheese		CH IY Z
	D,	//			dee			D IY
	DH,	//			thee		DH IY
	F,	//			fee			F IY
	G,	//			green		G R IY N
	HH,	//			he			HH IY
	JH,	//			gee			JH IY
	K,	//			key			K IY
	L,	//			lee			L IY
	M,	//			me			M IY
	N,	//			knee		N IY
	NG,	//			ping		P IH NG
	P,	//			pee			P IY
	R,	//			read		R IY D
	S,	//			sea			S IY
	SH,	//			she			SH IY
	T,	//			tea			T IY
	TH,	//			theta		TH EY T AH
	V,	//			vee			V IY
	W,	//			we			W IY
	Y,	//			yield		Y IY L D
	Z,	//			zee			Z IY
	ZH;	//		    seizure		S IY ZH ER

	//Consonants

	public boolean isConsonant() {
		return !this.isVowel();
	}

	private boolean isConsonant(PhonemeEnum p) {
		return !this.isVowel(p);
	}

	//Place of Articulation

	public static PlaceOfArticulation getPlace(PhonemeEnum p) {
		return p.getPlace();
	}

	public PlaceOfArticulation getPlace() {
		if (this.isAlveolar(this))
			return PlaceOfArticulation.ALVEOLAR;

		else if (this.isPalatal(this))
			return PlaceOfArticulation.PALATAL;

		else if (this.isBilabial(this))
			return PlaceOfArticulation.BILABIAL;

		else if (this.isVelar(this))
			return PlaceOfArticulation.VELAR;

		else if (this.isLabiodental(this))
			return PlaceOfArticulation.LABIODENTAL;

		else if (this.isInterdental(this))
			return PlaceOfArticulation.INTERDENTAL;

		else if (this.isGlottal(this))
			return PlaceOfArticulation.GLOTTAL;

		else return null;
	}

	private static boolean isAlveolar(PhonemeEnum p) {
		if (    p == T ||
				p == D ||
				p == S ||
				p == Z ||
				p == L ||
				p == R ||
				p == N )
			return true;
		return false;
	}

	private static boolean isPalatal(PhonemeEnum p) {
		if (    p == SH ||
				p == ZH ||
				p == CH ||
				p == JH ||
				p == Y )
			return true;
		return false;
	}

	private static boolean isBilabial(PhonemeEnum p) {
		if (    p == B ||
				p == P ||
				p == W ||
				p == M )
			return true;
		return false;
	}

	private static boolean isVelar(PhonemeEnum p) {
		if (    p == K ||
				p == G ||
				p == NG )
			return true;
		return false;
	}

	private static boolean isLabiodental(PhonemeEnum p) {
		if (    p == F ||
				p == V )
			return true;
		return false;
	}

	private static boolean isInterdental(PhonemeEnum p) {
		if (    p == TH ||
				p == DH )
			return true;
		return false;
	}

	private static boolean isGlottal(PhonemeEnum p) {
		if (    p == HH )
			return true;
		return false;
	}

	public boolean isVoiced() {
		return isVoiced(this);
	}

	private static boolean isVoiced(PhonemeEnum p) {
		if (    p.isVowel() ||
				p == B ||
				p == V ||
				p == D ||
				p == M ||
				p == G ||
				p == N ||
				p == Z ||
				p == L ||
				p == R ||
				p == NG ||
				p == ZH ||
				p == DH ||
				p == JH )
			return true;
		return false;
	}

    /*
    Vowels have 4 features:
    phonetics.Height - low, mid, high
    phonetics.Frontness - front, central, back
    Tenseness - primary or secondary
    Rounding - round or not
     */

	//Manner of Articulation

	public static MannerOfArticulation getManner(PhonemeEnum p) {
		return p.getManner();
	}

	public boolean isObstruent() {
		if (this.getManner() == MannerOfArticulation.STOP ||
				this.getManner() == MannerOfArticulation.FRICATIVE ||
				this.getManner() == MannerOfArticulation.AFFRICATE)
			return true;
		return false;
	}

	public MannerOfArticulation getManner() {
		if (this.isFricative(this))
			return MannerOfArticulation.FRICATIVE;

		else if (this.isStop(this))
			return MannerOfArticulation.STOP;

		else if (this.isNasal(this))
			return MannerOfArticulation.NASAL;

		else if (this.isAffricate(this))
			return MannerOfArticulation.AFFRICATE;

		else if (this.isLiquid(this))
			return MannerOfArticulation.LIQUID;

		else if (this.isSemivowel(this))
			return MannerOfArticulation.SEMIVOWEL;

		else if (this.isAspirate(this))
			return MannerOfArticulation.ASPIRATE;

		else return null;
	}

	private static boolean isFricative(PhonemeEnum p) {
		if (    p == DH ||
				p == F ||
				p == S ||
				p == SH ||
				p == TH ||
				p == V ||
				p == Z ||
				p == ZH )
			return true;
		return false;
	}

	private static boolean isStop(PhonemeEnum p) {
		if (    p == B ||
				p == D ||
				p == G ||
				p == K ||
				p == P ||
				p == T )
			return true;
		return false;
	}

	private static boolean isNasal(PhonemeEnum p) {
		if (    p == M ||
				p == N ||
				p == NG )
			return true;
		return false;
	}

	private static boolean isAffricate(PhonemeEnum p) {
		if (    p == CH ||
				p == JH )
			return true;
		return false;
	}

	private static boolean isLiquid(PhonemeEnum p) {
		if (    p == L ||
				p == R )
			return true;
		return false;
	}

	private static boolean isSemivowel(PhonemeEnum p) {
		if (    p == W ||
				p == Y )
			return true;
		return false;
	}

	private static boolean isAspirate(PhonemeEnum p) {
		if (    p == HH )
			return true;
		return false;
	}

	//Vowels

	public boolean isVowel() {
		if (this.isVowel(this))
			return true;
		return false;
	}

	private static boolean isVowel(PhonemeEnum p) {
		if (    p == AA ||
				p == AE ||
				p == AH ||
				p == AO ||
				p == AW ||
				p == AY ||
				p == EH ||
				p == ER ||
				p == EY ||
				p == IH ||
				p == IY ||
				p == OW ||
				p == OY ||
				p == UH ||
				p == UW )
			return true;
		return false;
	}

    /*
    x is frontness, 0 = front, 9 = back
    y is height, 0 = low, 9 = high
     */

	public static double[] getCoord(PhonemeEnum p) {
		if (p == null)
			return null;
		if (p.isDiphthong(p)) {
			PhonemeEnum[] parts = getDiphthongParts(p);
			double[] coord0 = getCoord(parts[0]);
			double[] coord1 = getCoord(parts[1]);
			double[] result = new double[2];
			result[0] = (coord0[0] + coord1[0]) / 2;
			result[1] = (coord0[1] + coord1[1]) / 2;
			return result;
		}
		else if (p == IY)
			return new double[] {0,9};
		else  if (p == IH)
			return new double[] {2,8};
		else if (p == EY)
			return new double[] {1,6};
		else if (p == EH)
			return new double[] {3,4};
		else if (p == AE)
			return new double[] {4,1};
		else if (p == AH)
			return new double[] {6.5,4.5};
		else if (p == UW)
			return new double[] {9,9};
		else if (p == UH)
			return new double[] {8,7};
		else if (p == OW)
			return new double[] {9,6};
		else if (p == AO)
			return new double[] {8,4};
		else if (p == AA)
			return new double[] {8,1};
		else if (p == ER)
			return new double[] {4,7};
		return null;
	}


	//Diphthongs

	public static boolean isDiphthong(PhonemeEnum p) {
		if (    p == AY ||
				p == AW ||
				p == OY )
			return true;
		return false;
	}

	public static PhonemeEnum[] getDiphthongParts(PhonemeEnum p) {
		if (!isDiphthong(p))
			return null;
		if (p == AY)
			return new PhonemeEnum[] {AH, IY};
		else if (p == AW)
			return new PhonemeEnum[] {AH, UH};
		else if (p == OY)
			return new PhonemeEnum[] {AO, IH};
		return null;
	}

	//Roundedness

	public Roundness getRoundness() {
		if (this.isRounded(this)) return Roundness.ROUND;
		return Roundness.NOT_ROUND;
	}

	public static boolean isRounded(PhonemeEnum p) {
		//TODO check w/ mom
		if (!p.isVowel()) return false;
		else if (p == UW ||
				p == UH ||
				p == OY ||
				p == OW ||
				p == AO )
			return true;
		return false;
	}

	//Lax / primary

	public Tension getTension() {
		if (this.isTense(this)) return Tension.TENSE;
		return Tension.LAX;
	}

	public static boolean isTense(PhonemeEnum p) {
		//TODO check w/ mom
		if (!p.isVowel()) return false;
		//secondary vowel phonemes
		else if (p == IY ||
				p == EY ||
				p == UW ||
				p == OW )
			return false;
		return true;
	}

	//phonetics.Frontness

	public Frontness getFrontness() {
		if (this.isFront(this))
			return Frontness.FRONT;

		else if (this.isCentral(this))
			return Frontness.CENTRAL;

		else if (this.isBack(this))
			return Frontness.BACK;

		return null;
	}

	private static boolean isFront(PhonemeEnum p) {
		if (!p.isVowel()) return false;
		else if (p == IY ||
				p == IH ||
				p == EH ||
				p == EY ||
				p == AE ||

				//dipthongs
				p == AY ||
				p == OY )
			return true;
		return false;
	}

	private static boolean isCentral(PhonemeEnum p) {
		if (!p.isVowel()) return false;
		else if (
				p == AH ||
				p == ER )
			return true;
		return false;
	}

	private static boolean isBack(PhonemeEnum p) {
		if (!p.isVowel()) return false;
		else if (p == UW ||
				p == AA ||
				p == AO ||
				p == UH ||
				p == OW ||

			//dipthong
				p ==  AW)
			return true;
		return false;
	}

	//phonetics.Height

	public Height getHeight() {
		if (this.isHigh(this))
			return Height.HIGH;


		else if (this.isMid(this))
			return Height.MID;

		else if (this.isLow(this))
			return Height.LOW;

		return null;
	}

	private static boolean isLow(PhonemeEnum p) {
		if (!p.isVowel()) return false;
		else if (p == AA ||
				p == AE )
			return true;
		return false;
	}

	private static boolean isMid(PhonemeEnum p) {
		if (!p.isVowel()) return false;
		else if (p == EH ||
				p == EY ||
				p == ER ||
				p == AH ||
				p == AO ||
				p == OW )
			return true;
		return false;
	}

	private static boolean isHigh(PhonemeEnum p) {
		if (!p.isVowel()) return false;
		else if (p == IY ||
				p == IH ||
				p == UH ||
				p == UW ||

				//dipthongs, end high
				p == OY ||
				p == AW ||
				p == AY )
			return true;
		return false;
	}
}
