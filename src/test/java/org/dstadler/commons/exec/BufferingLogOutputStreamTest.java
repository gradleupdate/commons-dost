package org.dstadler.commons.exec;

import java.io.IOException;
import java.util.logging.Level;

import org.dstadler.commons.testing.TestHelpers;
import org.dstadler.commons.testing.ThreadTestHelper;
import org.junit.Test;


/**
 *
 * @author dominik.stadler
 */
@SuppressWarnings("Convert2Lambda")
public class BufferingLogOutputStreamTest {
	private static final int NUMBER_OF_THREADS = 10;
	private static final int NUMBER_OF_TESTS = 1000;

	@Test
	public void test() throws IOException {
		try (BufferingLogOutputStream stream = new BufferingLogOutputStream()) {

    		// sends everything to Level.INFO
    		stream.processLine("someline", 0);
			stream.processLine("someline", 0);

    		// test null and empty string
    		stream.processLine(null, 0);
    		stream.processLine("", 0);

			stream.processLine("someline", 0);
		}

		// TODO: test if the data actually is buffered and reaches the log output at some point.
	}

	@Test
	public void testLargeData() {
		TestHelpers.runTestWithDifferentLogLevel(new Runnable() {
			@Override
			public void run() {
				try (BufferingLogOutputStream stream = new BufferingLogOutputStream()) {

    				// sends everything to Level.INFO
    				for (int i = 0; i < 1000; i++) {
    					stream.processLine("someline", 0);
    				}

					stream.close();

					// try closing again, should not fail
					stream.close();
				} catch (IOException e) {
				    throw new IllegalStateException(e);
                }
			}
		}, BufferingLogOutputStream.class.getName(), Level.WARNING);
	}

	@Test
	public void testMultipleThreads() throws Throwable {
		TestHelpers.runTestWithDifferentLogLevel(new Runnable() {
			@Override
			public void run() {
				try {
					ThreadTestHelper helper =
							new ThreadTestHelper(NUMBER_OF_THREADS, NUMBER_OF_TESTS);

					try (final BufferingLogOutputStream stream = new BufferingLogOutputStream()) {
						helper.executeTest(new ThreadTestHelper.TestRunnable() {

							@Override
							public void doEnd(int threadnum) throws Exception {
								// do stuff at the end ...
							}

							@Override
							public void run(int threadnum, int iter) throws Exception {
								for (int i = 0; i < 100; i++) {
									stream.processLine("someline", 0);
								}
							}
						});
					}
				} catch (Throwable e) {
					throw new IllegalStateException(e);
				}
			}
		}, BufferingLogOutputStream.class.getName(), Level.WARNING);
	}
}
