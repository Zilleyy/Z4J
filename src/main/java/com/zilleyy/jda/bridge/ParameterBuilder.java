package com.zilleyy.jda.bridge;

import java.util.LinkedList;

/**
 * Author: Zilleyy
 * <br>
 * Date: 1/04/2021 @ 2:31 pm AEST
 */
public class ParameterBuilder {

    private final String path;
    private final LinkedList<String> params;

    public ParameterBuilder(final String path) {
        this.path = path;
        this.params = new LinkedList<>();
    }

    public final ParameterBuilder add(final String name, final String value) {
        this.params.add(name + "=" + value);
        return this;
    }

    public final String build() {
        String result = this.path + (this.params.size() > 0 ? "?" : "");
        for(int i = 0; i < this.params.size(); i++) {
            result += this.params.get(i) + (this.params.size() - 1 != i ? "&" : "");
        }
        return result;
    }

}
