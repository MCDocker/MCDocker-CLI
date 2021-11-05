/*
 *
 *   MCDocker, an open source Minecraft launcher.
 *   Copyright (C) 2021 MCDocker
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.mcdocker.cli.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableCreator {

    private static TableCreator instance;
    public static TableCreator getInstance() {
        if (instance == null) instance = new TableCreator();
        return instance;
    }

    private List<String> headers = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();

    public TableCreator addHeaderItem(String item) {
        headers.add(item);
        return this;
    }

    public TableCreator addHeaderItems(List<String> items) {
        headers.addAll(items);
        return this;
    }

    public TableCreator setHeaders(List<String> items) {
        headers = items;
        return this;
    }

    public TableCreator addRowItem(int index, String item) {
        rows.get(index).add(item);
        return this;
    }

    public TableCreator addRow(List<String> items) {
        rows.add(items);
        return this;
    }

    public TableCreator addRow(String... items) {
        List<String> row = new ArrayList<>();
        Collections.addAll(row, items);
        rows.add(row);
        return this;
    }

    public TableCreator setRow(int index, List<String> items) {
        rows.set(index, items);
        return this;
    }

    public TableCreator setRows(List<List<String>> items) {
        rows = items;
        return this;
    }

    public TableCreator setHeaders(String... items) {
        headers = Arrays.asList(items);
        return this;
    }

    public String getOutput() {
        StringBuilder out = new StringBuilder();

        // TODO Make table

        return out.toString();

    }

}
