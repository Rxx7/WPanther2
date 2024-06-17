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
package com.ayanix.panther.storage;

import com.ayanix.panther.storage.configuration.Configuration;

import java.io.File;

/**
 * Panther - Developed by Lewes D. B.
 * All rights reserved 2017.
 */
public interface YAMLStorage
{

	/**
	 * Save configuration to file.
	 */
	void save();

	/**
	 * Discard any unsaved changes and load file.
	 */
	void reload();

	/**
	 * Get the corresponding file.
	 *
	 * @return The file the storage is saved and loaded from.
	 */
	File getFile();

	/**
	 * The YamlConfiguration config.
	 *
	 * @return The configuration.
	 */
	Configuration getConfig();

	/**
	 * Insert default configuration to replace missing values.
	 *
	 * @param defaultStorage Default configuration.
	 */
	void insertDefault(DefaultStorage defaultStorage);

}
