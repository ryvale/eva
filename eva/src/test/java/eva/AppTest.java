package eva;

import com.exa.expression.XPOperand;
import com.exa.expression.eval.XPEvaluator;
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
		XPEvaluator evaluator = parser.evaluator();
		
		XPOperand<?> xp = parser.parseString("'OK'");
		assertTrue("OK".equals(xp.value(evaluator)));
		
		xp = parser.parseString("substr('OK EVA-EXA', 0, 2)");
		String strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("2 - 1");
		Integer intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(1).equals(intRes));
		
		parser.evaluator().addVariable("str", String.class, "OK EVA");
		xp = parser.parseString("str");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EVA".equals(strRes));
		
		parser.evaluator().addVariable("this", String.class, "OK EVA");
		xp = parser.parseString("length");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(6).equals(intRes));
		
		xp = parser.parseString("str.length");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(6).equals(intRes));
		
		xp = parser.parseString("str.substr(0, 1)");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("O".equals(strRes));
		
		xp = parser.parseString("7.0");
		Double dblRes = xp.asOPDouble().value(evaluator);
		System.out.println(dblRes);
		assertTrue(new Double(7).equals(dblRes));
		
		xp = parser.parseString("7d");
		dblRes = xp.asOPDouble().value(evaluator);
		System.out.println(dblRes);
		assertTrue(new Double(7).equals(dblRes));
		
		xp = parser.parseString("(1+2)");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(3).equals(intRes));
		
		
		evaluator.classesMan().registerClass(new TTest());
		xp = parser.parseString("test.execute()");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("test.sqlString('Sonia')");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("'Sonia'".equals(strRes));
	}
	
	public void testNiveau1() throws ManagedException {
		Parser parser = new Parser();
		XPEvaluator evaluator = parser.evaluator();
		
		XPOperand<?> xp = parser.parseString("'OK' + ' EXA'");
		
		String strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EXA".equals(strRes));
		
		xp = parser.parseString("'OK' + ' EVA' + '-EXA'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK'+ ' EVA' + '-EXA'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK' +' EVA' + '-EXA'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK' +' EVA' +'-EXA'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK' +' EVA'+'-EXA'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("'OK'+' EVA'+'-EXA'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EVA-EXA".equals(strRes));
		
		xp = parser.parseString("substr('OK EVA-EXA', 0, 2) + ' EVA'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EVA".equals(strRes));
		
		xp = parser.parseString("'EVA ' + substr('OK EVA-EXA', 0, 2)");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("EVA OK".equals(strRes));
		
		xp = parser.parseString("'EVA ' +substr('OK EVA-EXA', 0, 2)");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("EVA OK".equals(strRes));
		
		parser.evaluator().addVariable("str", String.class, "OK");
		xp = parser.parseString("str + ' EXA'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EXA".equals(strRes));
		
		xp = parser.parseString("str + str");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OKOK".equals(strRes));
		
		xp = parser.parseString("str + ' EXA ' + str");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EXA OK".equals(strRes));
		
		xp = parser.parseString("str+ ' EXA ' + str");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EXA OK".equals(strRes));
		
		xp = parser.parseString("str+ ' EXA '+str");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EXA OK".equals(strRes));
		
		xp = parser.parseString("str+' EXA '+str");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK EXA OK".equals(strRes));
		
		xp = parser.parseString("substr(str, 0, 1) + 'K'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("str.substr(0, 1) + 'K'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("(3+4)*5");
		Integer intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(35).equals(intRes));
		
		parser.evaluator().addVariable("this", String.class, "OK EVA");
		xp = parser.parseString("2 + length");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(8).equals(intRes));
		
		xp = parser.parseString("2 + length + 2");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(10).equals(intRes));
		
		xp = parser.parseString("length + 2");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(8).equals(intRes));
		
		xp = parser.parseString("str.length + length");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(8).equals(intRes));
	}
	
	public void testNiveau2() throws ManagedException {
		Parser parser = new Parser();
		XPEvaluator evaluator = parser.evaluator();
		
		XPOperand<?> xp = parser.parseString("substr(substr('OK EVA-EXA', 0, 4), 0, 2)");
		String strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("substr('OK ' + 'EVA-EXA', 0, 2)");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		parser.evaluator().addVariable("str", String.class, "OK EVA-EXA");
		xp = parser.parseString("substr(str + ' OK', 0, 2)");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("str.substr(0, 4).substr(0, 2)");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("str.substr(0, 4).length");
		int intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(4).equals(intRes));
		
		xp = parser.parseString("str.substr(0, 4).length - 1");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(3).equals(intRes));
		
		xp = parser.parseString("2*3+1");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(7).equals(intRes));
		
		xp = parser.parseString("7.0 + 5.0");
		Double dblRes = xp.asOPDouble().value(evaluator);
		System.out.println(dblRes);
		assertTrue(new Double(12).equals(dblRes));
		
		xp = parser.parseString("7.0 + 5");
		dblRes = xp.asOPDouble().value(evaluator);
		System.out.println(dblRes);
		assertTrue(new Double(12).equals(dblRes));
		
		
		xp = parser.parseString("8 + 7.0");
		dblRes = xp.asOPDouble().value(evaluator);
		System.out.println(dblRes);
		assertTrue(new Double(15).equals(dblRes));
		
		xp = parser.parseString("8 + 7.0 -1");
		dblRes = xp.asOPDouble().value(evaluator);
		System.out.println(dblRes);
		assertTrue(new Double(14).equals(dblRes));
		
		xp = parser.parseString("3 + 7.0 * 2");
		dblRes = xp.asOPDouble().value(evaluator);
		System.out.println(dblRes);
		assertTrue(new Double(17).equals(dblRes));
		
		xp = parser.parseString("(1 + 2 * (1+2) )*5");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(35).equals(intRes));
	}
	
	
}
