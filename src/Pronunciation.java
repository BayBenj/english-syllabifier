import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pronunciation extends ArrayList<Phoneme> implements Serializable {

	public List<PhonemeEnum> getPhonemeEnums() {
		List<PhonemeEnum> result = new ArrayList<>();
		for (Phoneme p : this)
			result.add(p.getPhonemeEnum());
		return result;
	}

	@Override
	public String toString() {
		String sb = "[";
		int i = 0;
		for (Phoneme phoneme : this) {
			if (i != 0)
				sb+="-";
			sb += phoneme.toString();
			i++;
		}
		sb += "]";
		return sb;
	}
}
