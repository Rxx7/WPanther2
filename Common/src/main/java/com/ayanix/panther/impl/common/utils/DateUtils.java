/*
 *                                                            _...---.._
 *                                                        _.'`       -_ ``.
 *                                                    .-'`                 `.
 *                                                 .-`                     q ;
 *                                              _-`                       __  \
 *                                          .-'`                  . ' .   \ `;/
 *                                      _.-`                    /.      `._`/
 *                              _...--'`                        \_`..._
 *                           .'`                         -         `'--:._
 *                        .-`                           \                  `-.
 *                       .                               `-..__.....----...., `.
 *                      '                   `'''---..-''`'              : :  : :
 *                    .` -                '``                           `'   `'
 *                 .-` .` '             .``
 *             _.-` .-`   '            .
 *         _.-` _.-`    .' '         .`
 * (`''--'' _.-`      .'  '        .'
 *  `'----''        .'  .`       .`
 *                .'  .'     .-'`    _____               _    _
 *              .'   :    .-`       |  __ \             | |  | |
 *              `. .`   ,`          | |__) |__ _  _ __  | |_ | |__    ___  _ __
 *               .'   .'            |  ___// _` || '_ \ | __|| '_ \  / _ \| '__|
 *              '   .`              | |   | (_| || | | || |_ | | | ||  __/| |
 *             '  .`                |_|    \__,_||_| |_| \__||_| |_| \___||_|
 *             `  '.
 *             `.___;
 */
package com.ayanix.panther.impl.common.utils;

import com.ayanix.panther.utils.common.IDateUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 * <p>
 * The majority of the source in this class was taken from the EssentialsX repository.
 */
public class DateUtils implements IDateUtils
{

	private static final Pattern TIME_PATTERN = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);
	private static final int     MAX_YEARS    = 100000;
	private static DateUtils instance;

	public DateUtils()
	{
		// DateUtils is an API.
	}

	/**
	 * Grab the static version of DateUtils.
	 *
	 * @return DateUtils.
	 */
	public static DateUtils get()
	{
		if (instance == null)
		{
			instance = new DateUtils();
		}

		return instance;
	}

	@Override
	public boolean shouldExpire(long unixTime)
	{
		return unixTime < (System.currentTimeMillis() / 1000);
	}

	@Override
	public long parseDateDiff(String time, boolean future) throws IllegalArgumentException
	{
		Matcher m       = TIME_PATTERN.matcher(time);
		int     years   = 0;
		int     months  = 0;
		int     weeks   = 0;
		int     days    = 0;
		int     hours   = 0;
		int     minutes = 0;
		int     seconds = 0;
		boolean found   = false;

		while (m.find())
		{
			if (m.group() == null || m.group().isEmpty())
			{
				continue;
			}

			for (int i = 0; i < m.groupCount(); i++)
			{
				if (m.group(i) != null && !m.group(i).isEmpty())
				{
					found = true;
					break;
				}
			}

			if (found)
			{
				if (m.group(1) != null && !m.group(1).isEmpty())
				{
					years = Integer.parseInt(m.group(1));
				}

				if (m.group(2) != null && !m.group(2).isEmpty())
				{
					months = Integer.parseInt(m.group(2));
				}

				if (m.group(3) != null && !m.group(3).isEmpty())
				{
					weeks = Integer.parseInt(m.group(3));
				}

				if (m.group(4) != null && !m.group(4).isEmpty())
				{
					days = Integer.parseInt(m.group(4));
				}

				if (m.group(5) != null && !m.group(5).isEmpty())
				{
					hours = Integer.parseInt(m.group(5));
				}

				if (m.group(6) != null && !m.group(6).isEmpty())
				{
					minutes = Integer.parseInt(m.group(6));
				}

				if (m.group(7) != null && !m.group(7).isEmpty())
				{
					seconds = Integer.parseInt(m.group(7));
				}

				break;
			}
		}

		if (!found)
		{
			throw new IllegalArgumentException("Date is invalid");
		}

		Calendar c = new GregorianCalendar();

		if (years > 0)
		{
			if (years > MAX_YEARS)
			{
				years = MAX_YEARS;
			}
			c.add(Calendar.YEAR, years * (future ? 1 : -1));
		}

		if (months > 0)
		{
			c.add(Calendar.MONTH, months * (future ? 1 : -1));
		}

		if (weeks > 0)
		{
			c.add(Calendar.WEEK_OF_YEAR, weeks * (future ? 1 : -1));
		}

		if (days > 0)
		{
			c.add(Calendar.DAY_OF_MONTH, days * (future ? 1 : -1));
		}

		if (hours > 0)
		{
			c.add(Calendar.HOUR_OF_DAY, hours * (future ? 1 : -1));
		}

		if (minutes > 0)
		{
			c.add(Calendar.MINUTE, minutes * (future ? 1 : -1));
		}

		if (seconds > 0)
		{
			c.add(Calendar.SECOND, seconds * (future ? 1 : -1));
		}

		Calendar max = new GregorianCalendar();
		max.add(Calendar.YEAR, 10);

		if (c.after(max))
		{
			return (max.getTimeInMillis() / 1000) + 1;
		}

		return (c.getTimeInMillis() / 1000) + 1;
	}

	@Override
	public String formatDateDiff(long unixTime)
	{
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(unixTime * 1000);
		Calendar now = new GregorianCalendar();
		return formatDateDiff(now, c);
	}

	private String formatDateDiff(Calendar fromDate, Calendar toDate)
	{
		boolean future = false;

		if (toDate.equals(fromDate))
		{
			return "now";
		}

		if (toDate.after(fromDate))
		{
			future = true;
		}

		StringBuilder sb       = new StringBuilder();
		int[]         types    = new int[]{Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND};
		String[]      names    = new String[]{"year", "years", "month", "months", "day", "days", "hour", "hours", "minute", "minutes", "second", "seconds"};
		int           accuracy = 0;

		for (int i = 0; i < types.length; i++)
		{
			if (accuracy > 2)
			{
				break;
			}

			int diff = dateDiff(types[i], fromDate, toDate, future);

			if (diff > 0)
			{
				accuracy++;
				sb.append(' ').append(diff).append(' ').append(names[i * 2 + (diff > 1 ? 1 : 0)]);
			}
		}

		if (sb.length() == 0)
		{
			return "now";
		}

		return sb.toString().trim();
	}

	private int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future)
	{
		int year = Calendar.YEAR;

		int fromYear = fromDate.get(year);
		int toYear   = toDate.get(year);

		if (Math.abs(fromYear - toYear) > MAX_YEARS)
		{
			toDate.set(year, fromYear + (future ? MAX_YEARS : -MAX_YEARS));
		}

		int  diff      = 0;
		long savedDate = fromDate.getTimeInMillis();

		while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate)))
		{
			savedDate = fromDate.getTimeInMillis();
			fromDate.add(type, future ? 1 : -1);
			diff++;
		}

		diff--;
		fromDate.setTimeInMillis(savedDate);

		return diff;
	}

}