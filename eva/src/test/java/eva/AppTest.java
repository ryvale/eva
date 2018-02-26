package eva;

import com.exa.expression.XPOperand;
import com.exa.expression.parsing.Parser;
import com.exa.utils.ManagedException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

	public AppTest( String testName ) {
        super( testName );
    }
	
	public static Test suite()  {
        return new TestSuite( AppTest.class );
    }
	
	public void testBasic() throws ManagedException {
		Parser parser = new Parser();
		
		XPOperand<?> xp = parser.parseString("'OK'");
		assertTrue("OK".equals(xp.value()));
		
		
		
		xp = parser.parseString("substr('OK EVA-EXA', 0, 2)");
		String strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
	}
	
	public void testNiveau1() throws ManagedException {
		Parser parser = new Parser();
		
		XPOperand<?> xp = parser.parseString("'OK' + ' EXA'");
		
		String strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK EXA".equals(strRes));
		
		xp = parser.parseString("'OK' + ' EVA' + '-EXA'");
		strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK'+ ' EVA' + '-EXA'");
		strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK' +' EVA' + '-EXA'");
		strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK' +' EVA' +'-EXA'");
		strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK' +' EVA'+'-EXA'");
		strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK'+' EVA'+'-EXA'");
		strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("substr('OK EVA-EXA', 0, 2) + ' EVA'");
		strRes = xp.asOPString().value();
		System.out.println(strRes);
		assertTrue("OK EVA".equals(strRes));
	}
}
