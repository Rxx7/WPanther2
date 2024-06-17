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
package com.ayanix.panther.utils.common;

import java.time.DateTimeException;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface IDateUtils
{

	/**
	 * Get whether or not a unix timestamp is in the past.
	 *
	 * @param unixTime The unix timestamp.
	 * @return If the timestamp has expired.
	 */
	boolean shouldExpire(long unixTime);

	/**
	 * Converts a time string to a unix timestamp.
	 *
	 * @param time   The time string.
	 * @param future If the date is in the future, as opposed to the past.
	 * @return A unix timestamp.
	 * @throws DateTimeException if the date input was invalid.
	 */
	long parseDateDiff(String time, boolean future) throws IllegalArgumentException;

	/**
	 * Converts a unix timestamp to string format of time distance (1 years, 2 months etc)
	 *
	 * @param unixTime The unix timestamp in future or past.
	 * @return A date difference between unixTime and current time.
	 */
	String formatDateDiff(long unixTime);

}
