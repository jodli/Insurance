/*
 * Copyright (C) 2013 Jan-Olaf Becker
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package src.jodli.Client.log;

public enum LogType {
	DEBUG, EXTENDED, MINIMAL;

	private static Integer currentPrecedence;
	private int precedence = currentPrecedence();

	public boolean includes(LogType other) {
		return other.precedence >= this.precedence;
	}

	public String toString() {
		return name().substring(0, 1) + name().substring(1).toLowerCase();
	}

	private int currentPrecedence() {
		if (currentPrecedence == null) {
			currentPrecedence = 0;
		}
		return currentPrecedence++;
	}
}
