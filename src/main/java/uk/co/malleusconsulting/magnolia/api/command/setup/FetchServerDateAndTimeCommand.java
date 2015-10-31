package uk.co.malleusconsulting.magnolia.api.command.setup;

import info.magnolia.commands.MgnlCommand;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FetchServerDateAndTimeCommand extends MgnlCommand {

    static final String ATTRIBUTE_KEY_DATE = "formattedDate";
    static final String ATTRIBUTE_KEY_TIME = "formattedTime";

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm";

    private String dateFormat;
    private String timeFormat;

    @Override
    public boolean execute(Context context) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat != null ? dateFormat : DEFAULT_DATE_FORMAT);
        SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat != null ? timeFormat : DEFAULT_TIME_FORMAT);

        Date timeNow = new Date();
        MgnlContext.setAttribute(ATTRIBUTE_KEY_DATE, dateFormatter.format(timeNow));
        MgnlContext.setAttribute(ATTRIBUTE_KEY_TIME, timeFormatter.format(timeNow));

        return true;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }
}
