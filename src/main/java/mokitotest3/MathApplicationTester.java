package mokitotest3;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;


// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {

	MathApplication mathApplication;
	CalculatorService calcService;

	@Before
	public void setUp() {
		mathApplication = new MathApplication();
		calcService = mock(CalculatorService.class);
		mathApplication.setCalculatorService(calcService);
	}

	@Test
	public void testAddAndSubtract() {

		// add the behavior to add numbers
		when(calcService.add(20.0, 10.0)).thenReturn(30.0);

		// subtract the behavior to subtract numbers
		when(calcService.subtract(20.0, 10.0)).thenReturn(10.0);

		// test the subtract functionality
		Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

		// test the add functionality
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

		// verify call to calcService is made or not
		verify(calcService).add(20.0, 10.0);
		verify(calcService).subtract(20.0, 10.0);

		// create an inOrder verifier for a single mock
		InOrder inOrder = inOrder(calcService);

		// following will make sure that add is first called then subtract is called.
		inOrder.verify(calcService).subtract(20.0, 10.0);
		inOrder.verify(calcService).add(20.0, 10.0);

	}

	@Test
	public void testSubtract() {
		// add the behavior to add numbers
		when(calcService.subtract(40.0, 10.0)).thenAnswer(new Answer<Double>() {

			@Override
			public Double answer(InvocationOnMock invocation) throws Throwable {
				// get the arguments passed to mock
				Object[] args = invocation.getArguments();

				// get the mock
				Object mock = invocation.getMock();

				// return the result
				return 30.0;
			}
		});

		// test the add functionality
		Assert.assertEquals(mathApplication.subtract(40.0, 10.0), 30.0, 0);
	}

	@Test
	public void testAddAndSubtract2() {

		// add the behavior to add numbers
		when(calcService.add(20.0, 10.0)).thenReturn(30.0);

		// test the add functionality
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

		// reset the mock
		reset(calcService);

		// test the add functionality after resetting the mock
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 0.0, 0);
	}

	@Test
	public void testAdd2() {

		// Given
		given(calcService.add(20.0, 10.0)).willReturn(30.0);

		// when
		double result = calcService.add(20.0, 10.0);

		// then
		Assert.assertEquals(result, 30.0, 0);
	}

	@Test
	public void testAddAndSubtract3() {

		// add the behavior to add numbers
		when(calcService.add(20.0, 10.0)).thenReturn(30.0);

		// subtract the behavior to subtract numbers
		when(calcService.subtract(20.0, 10.0)).thenReturn(10.0);

		// test the subtract functionality
		Assert.assertEquals(mathApplication.subtract(20.0, 10.0), 10.0, 0);

		// test the add functionality
		Assert.assertEquals(mathApplication.add(20.0, 10.0), 30.0, 0);

		// verify call to add method to be completed within 100 ms
		verify(calcService, timeout(1000000)).add(20.0, 10.0);

		// invocation count can be added to ensure multiplication invocations
		// can be checked within given timeframe
		verify(calcService, timeout(1000000).times(1)).subtract(20.0, 10.0);
	}
}