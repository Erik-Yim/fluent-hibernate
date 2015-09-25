package com.github.fluent.hibernate.test.util;

import com.github.fluent.hibernate.test.persistent.transformer.Root;
import com.github.fluent.hibernate.test.persistent.transformer.RootStationar;
import com.github.fluent.hibernate.test.persistent.transformer.Stationar;

/**
 *
 * @author V.Ladynev
 */
public final class FluentHibernateTransformerTestData {

    public static final String ROOT_NAME_A = "A rootName";

    public static final String STATIONAR_NAME_A = "A stationarName";

    public static final String ROOT_NAME_B = "B rootName";

    public static final String STATIONAR_NAME_B = "B stationarName";

    public static Root rootA() {
        return new RootBuilder("A").build();
    }

    public static Root rootB() {
        return new RootBuilder("B").build();
    }

    private static final class RootBuilder {

        private final String prefix;

        private RootBuilder(String prefix) {
            this.prefix = prefix;
        }

        public Root build() {
            Root result = new Root();
            result.setRootName(p("rootName"));
            result.setStationarFrom(createRootStationar());
            return result;
        }

        private RootStationar createRootStationar() {
            RootStationar result = new RootStationar();
            result.setComment(p("rootStationarComment"));
            result.setStationar(createStationar());
            return result;
        }

        private Stationar createStationar() {
            Stationar result = new Stationar();
            result.setName(p("stationarName"));
            return result;
        }

        private String p(String name) {
            return prefix + " " + name;
        }

    }

}
