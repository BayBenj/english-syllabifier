import javafx.geometry.Pos;
import java.util.List;

public class SyllableToken extends Token {

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SyllableToken that = (SyllableToken) o;

		if (getCountOfSylsInContext() != that.getCountOfSylsInContext()) return false;
		if (getPositionInContext() != that.getPositionInContext()) return false;
		if (getStress() != that.getStress()) return false;
		if (!getPhonemes().equals(that.getPhonemes())) return false;
		if (getPos() != that.getPos()) return false;
		return getStringRepresentation().equals(that.getStringRepresentation());
	}

	@Override
	public int hashCode() {
		int result = getPhonemes().hashCode();
		result = 31 * result + getPos().hashCode();
		result = 31 * result + getCountOfSylsInContext();
		result = 31 * result + getPositionInContext();
		result = 31 * result + getStress();
		result = 31 * result + getStringRepresentation().hashCode();
		return result;
	}

	@Override
	public String toString() {
		return ""+ phonemes + stress;// + ", " + pos + ", " + countOfSylsInContext + ", " + positionInContext + ", " + stress;
	}

	private List<PhonemeEnum> phonemes;
	private Pos pos;
	private int countOfSylsInContext;
	private int positionInContext;
	private int stress;
	private String stringRepresentation;
	// double uniqueness; future feature
	// rhyme class; future feature

	public SyllableToken(String stringRepresentation, List<PhonemeEnum> phonemes, Pos pos, int countOfSylsInContext, int positionInContext, int stress) {
		this.setPhonemes(phonemes);
		this.setPos(pos);
		this.setCountOfSylsInContext(countOfSylsInContext);
		this.setPositionInContext(positionInContext);
		this.setStress(stress);
		this.setStringRepresentation(stringRepresentation);
	}

	public List<PhonemeEnum> getPhonemes() {
		return phonemes;
	}

	public void setPhonemes(List<PhonemeEnum> phonemes) {
		this.phonemes = phonemes;
	}

	public Pos getPos() {
		return pos;
	}

	public void setPos(Pos pos) {
		this.pos = pos;
	}

	public int getCountOfSylsInContext() {
		return countOfSylsInContext;
	}

	public void setCountOfSylsInContext(int countOfSylsInContext) {
		this.countOfSylsInContext = countOfSylsInContext;
	}

	public int getPositionInContext() {
		return positionInContext;
	}

	public void setPositionInContext(int positionInContext) {
		this.positionInContext = positionInContext;
	}

	public int getStress() {
		return stress;
	}

	public void setStress(int stress) {
		this.stress = stress;
	}

	public String getStringRepresentation() {
		return stringRepresentation;
	}

	public void setStringRepresentation(String stringRepresentation) {
		this.stringRepresentation = stringRepresentation;
	}

	public String getStringRepresentationIfFirstSyllable() {
		if (positionInContext == 0) {
			return getStringRepresentation();
		}
		else return "";
	}

}
