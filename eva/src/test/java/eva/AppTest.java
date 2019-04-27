package eva;

import java.util.LinkedHashMap;
import java.util.Map;

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
		
		xp = parser.parseString("str.indexOf('OK')");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(0).equals(intRes));
		
		xp = parser.parseString("str.indexOf('EVA')");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(3).equals(intRes));
		
		xp = parser.parseString("str.indexOf('EVA') + 2");
		intRes = xp.asOPInteger().value(evaluator);
		System.out.println(intRes);
		assertTrue(new Integer(5).equals(intRes));
		
		Boolean blRes;
		xp = parser.parseString("5 > 0");
		blRes = xp.asOPBoolean().value(evaluator);
		assertTrue(Boolean.TRUE.equals(blRes));
		
		xp = parser.parseString("str.indexOf('EVA') >= 0");
		blRes = xp.asOPBoolean().value(evaluator);
		assertTrue(Boolean.TRUE.equals(blRes));
		
		xp = parser.parseString("str.indexOf('EVA') >= 0 || false");
		blRes = xp.asOPBoolean().value(evaluator);
		assertTrue(Boolean.TRUE.equals(blRes));
		
		xp = parser.parseString("str.indexOf('EVA') == 0 || false");
		blRes = xp.asOPBoolean().value(evaluator);
		assertTrue(Boolean.FALSE.equals(blRes));
		
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
		
		xp = parser.parseString("test.execute2('2')");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK2".equals(strRes));
		
		/*xp = parser.parseString("test.sqlString('Sonia')");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("'Sonia'".equals(strRes));*/
		
		xp = parser.parseString("2 == 2");
		blRes = xp.asOPBoolean().value(evaluator);
		System.out.println(blRes);
		assertTrue(Boolean.TRUE.equals(blRes));
		
		xp = parser.parseString("2 != 2");
		blRes = xp.asOPBoolean().value(evaluator);
		System.out.println(blRes);
		assertTrue(Boolean.FALSE.equals(blRes));
		
		xp = parser.parseString("2 != 1");
		blRes = xp.asOPBoolean().value(evaluator);
		System.out.println(blRes);
		assertTrue(Boolean.TRUE.equals(blRes));
		
		xp = parser.parseString("null == null");
		blRes = xp.asOPBoolean().value(evaluator);
		System.out.println(blRes);
		assertTrue(Boolean.TRUE.equals(blRes));
		
		xp = parser.parseString("true ? 'OK' : 'Non OK'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("true && true");
		blRes = xp.asOPBoolean().value(evaluator);
		assertTrue(Boolean.TRUE.equals(blRes));
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
		
		xp = parser.parseString("2 == 2 ? 'OK' : 'Non OK'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("2 == null ? 'OK' : 'Non OK'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("Non OK".equals(strRes));
		
		xp = parser.parseString("2 == null ? 'OK' : null");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue(strRes == null);
		
		xp = parser.parseString("str == 'OK' ? 'OK' : null");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("str == 'OK' ? 1 : 0.5");
		Double dblRes = xp.asOPDouble().value(evaluator);
		System.out.println(dblRes);
		assertTrue(new Double(1).equals(dblRes));
		
		xp = parser.parseString("2 == 2 ? (1 == 4 ? 'Non OK' : 'OK') : 'Non OK'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
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
		
		parser.evaluator().addVariable("str2", String.class, null);
		xp = parser.parseString("str2 == null ? 'OK' : 'KO'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
		xp = parser.parseString("str != null ? ('O' +'K') : 'KO'");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
	}
	
	public void testMethod() throws ManagedException {
		Parser parser = new Parser();
		XPEvaluator evaluator = parser.evaluator();
		parser.evaluator().addVariable("str", String.class, "OK EVA-EXA");
		
		
		Map<String, Object> mp = new LinkedHashMap<>();
		mp.put("start", "OK");
		GlobalParams gp = new GlobalParams(mp);
		evaluator.getClassesMan().registerClass(new TGlobalParams());
		evaluator.addVariable("x2", GlobalParams.class, gp);
		XPOperand<?>  xp = parser.parseString("x2.getString('start')");
		String strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("OK".equals(strRes));
		
	}
	
	public void testSpecifique() throws ManagedException {
		Parser parser = new Parser();
		XPEvaluator evaluator = parser.evaluator();
		parser.evaluator().addVariable("exploitation", String.class, "M41");
		parser.evaluator().addVariable("departement", String.class, null);
		
		XPOperand<?> xp = parser.parseString(" (departement == null ? \" like '\" + exploitation + \"%'\" : \"='\" + exploitation + \"-\" + departement + \"'\") ");
		//XPOperand<?> xp = parser.parseString("departement == null ? ' like ' + exploitation  + 'x' : 'OK'");
		//XPOperand<?> xp = parser.parseString("' like ' + exploitation + 'x'");
		String strRes = xp.asOPString().value(evaluator);
		assertTrue(" like 'M41%'".equals(strRes));
	}
	
	public void testDeepOp() throws ManagedException {
		Parser parser = new Parser();
		XPEvaluator evaluator = parser.evaluator();
		parser.evaluator().addVariable("str", String.class, "M41");
		//parser.evaluator().addVariable("departement", String.class, null);
		
		XPOperand<?> xp = parser.parseString("str.substr(0, 1) + str.substr(0, 1) ");
		
		String strRes = xp.asOPString().value(evaluator);
		
		System.out.println(strRes);
		assertTrue("MM".equals(strRes));
		
		
		xp = parser.parseString("str.substr(0, 1+1)");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("M4".equals(strRes));
		
		xp = parser.parseString("'x' + (str == '' ? '4' + str.substr(0, 2) + '5': 'y' + str + 'z') + 'a'+ str");
		strRes = xp.asOPString().value(evaluator);
		System.out.println(strRes);
		assertTrue("xyM41zaM41".equals(strRes));
	}
	
	
}
