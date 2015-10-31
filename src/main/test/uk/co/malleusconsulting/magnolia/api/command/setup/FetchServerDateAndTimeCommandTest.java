package uk.co.malleusconsulting.magnolia.api.command.setup;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;

public class FetchServerDateAndTimeCommandTest {

    private FetchServerDateAndTimeCommand command;
    private Context context;

    @Before
    public void setUp() {
        command = new FetchServerDateAndTimeCommand();
        Context mockContext = new MockContext();

        MgnlContext.setInstance(mockContext);
        context = MgnlContext.getInstance();
    }

    @Test
    public void commandAddsDefaultDateParameterToMgnlContext() throws Exception {
        boolean response = command.execute(context);
        assertThat(response, is(true));
        assertThat(context.containsKey(FetchServerDateAndTimeCommand.ATTRIBUTE_KEY_DATE), is(true));
        assertThat((String) context.getAttribute(FetchServerDateAndTimeCommand.ATTRIBUTE_KEY_DATE),
                matchesPattern("^\\d{4}-\\d{2}-\\d{2}$"));
    }

    @Test
    public void commandAddsDefaultTimeParameterToMgnlContext() throws Exception {
        boolean response = command.execute(context);
        assertThat(response, is(true));
        assertThat(context.containsKey(FetchServerDateAndTimeCommand.ATTRIBUTE_KEY_TIME), is(true));
        assertThat((String) context.getAttribute(FetchServerDateAndTimeCommand.ATTRIBUTE_KEY_TIME),
                matchesPattern("^\\d{2}:\\d{2}$"));
    }

    @Test
    public void commandAcceptsCustomDateFormat() throws Exception {
        command.setDateFormat("dd-MM-yyyy");
        boolean response = command.execute(context);
        assertThat(response, is(true));
        assertThat((String) context.getAttribute(FetchServerDateAndTimeCommand.ATTRIBUTE_KEY_DATE),
                matchesPattern("^\\d{2}-\\d{2}-\\d{4}$"));
    }

    @Test
    public void commandAcceptsCustomTimeFormat() throws Exception {
        command.setTimeFormat("h:mm a");
        boolean response = command.execute(context);
        assertThat(response, is(true));
        assertThat((String) context.getAttribute(FetchServerDateAndTimeCommand.ATTRIBUTE_KEY_TIME),
                matchesPattern("^\\d{1,2}:\\d{2} [A-Z]{2}$"));
    }
}
