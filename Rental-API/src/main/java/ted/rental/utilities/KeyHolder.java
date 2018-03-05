package ted.rental.utilities;

import java.security.Key;

import io.jsonwebtoken.impl.crypto.MacProvider;

public class KeyHolder {
	
	public static final Key key = MacProvider.generateKey();

}
