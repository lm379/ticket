import com.formdev.flatlaf.FlatLightLaf;

public class FlatLaf
	extends FlatLightLaf
{
	public static final String NAME = "FlatLaf";

	public static boolean setup() {
		return setup( new FlatLaf() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, FlatLaf.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
