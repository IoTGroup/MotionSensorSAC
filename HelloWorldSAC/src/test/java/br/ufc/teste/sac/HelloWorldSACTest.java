package br.ufc.teste.sac;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class HelloWorldSACTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HelloWorldSACTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( HelloWorldSACTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testHelloWorldSAC()
    {
        assertTrue( true );
    }
}
