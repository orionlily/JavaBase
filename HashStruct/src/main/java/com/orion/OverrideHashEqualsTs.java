package com.orion;

import java.util.HashSet;

/**
 * 重写equals必须要重写hashCode，在使用hash的结构数据工具时才能数据准确，如hashMap、hashSet、hashTable等
 *
 * 使用hashSet来判断，hashSet底层还是hashMap，putVal会判断hash和equals是否都相同，才认为是同一个元素key
 *
 * @author Administrator
 */
public class OverrideHashEqualsTs {

    public static void main(String[] args) {
        System.out.println("==========================既不重写equals也不重写hashCode==========================");

        HashSet<OverrideNeitherEqualsOrHash> overrideNeitherEqualsOrHash_hashSet = new HashSet<OverrideNeitherEqualsOrHash>();
        OverrideNeitherEqualsOrHash overrideNeitherEqualsOrHash_1 = new OverrideNeitherEqualsOrHash(1, "1");
        OverrideNeitherEqualsOrHash overrideNeitherEqualsOrHash_2 = new OverrideNeitherEqualsOrHash(1, "1");

        overrideNeitherEqualsOrHash_hashSet.add(overrideNeitherEqualsOrHash_1);
        overrideNeitherEqualsOrHash_hashSet.add(overrideNeitherEqualsOrHash_2);
        overrideNeitherEqualsOrHash_hashSet.add(overrideNeitherEqualsOrHash_1);

        System.out.println("overrideNeitherEqualsOrHash_1 hashCode : " + overrideNeitherEqualsOrHash_1.hashCode()
                + " , overrideNeitherEqualsOrHash_2 hashCode : " + overrideNeitherEqualsOrHash_2.hashCode()
                + ", they equals ? " + overrideNeitherEqualsOrHash_1.equals(overrideNeitherEqualsOrHash_2)
                + ", hashSet : " + overrideNeitherEqualsOrHash_hashSet
        );
        /* 结果：哈都没重写，该覆盖的覆盖
         * overrideNeitherEqualsOrHash_1 hashCode : 685325104 , overrideNeitherEqualsOrHash_2 hashCode : 460141958,
         * they equals ? false,
         * hashSet : [OverrideNeitherEqualsOrHash{age=1, name='1', hashCode='685325104},
         *            OverrideNeitherEqualsOrHash{age=1, name='1', hashCode='460141958}]
         */

        System.out.println();
        System.out.println("==========================只重写equals不重写hashCode==========================");

        HashSet<OverrideEqualsNotHash> overrideEqualsNotHash_hashSet = new HashSet<OverrideEqualsNotHash>();
        OverrideEqualsNotHash overrideEqualsNotHash_1 = new OverrideEqualsNotHash(1, "1");
        OverrideEqualsNotHash overrideEqualsNotHash_2 = new OverrideEqualsNotHash(1, "1");

        overrideEqualsNotHash_hashSet.add(overrideEqualsNotHash_1);
        overrideEqualsNotHash_hashSet.add(overrideEqualsNotHash_2);
        overrideEqualsNotHash_hashSet.add(overrideEqualsNotHash_1);

        System.out.println("overrideEqualsNotHash_1 hashCode : " + overrideEqualsNotHash_1.hashCode()
                + " , overrideEqualsNotHash_2 hashCode : " + overrideEqualsNotHash_2.hashCode()
                + ", they equals ? " + overrideEqualsNotHash_1.equals(overrideEqualsNotHash_2)
                + ", hashSet : " + overrideEqualsNotHash_hashSet
        );
        /* 结果：只是重写equals没有重写hashCode，hashSet底层还是hashMap，putVal会判断hash和equals是否都相同，才认为是同一个元素key
         * overrideEqualsNotHash_1 hashCode : 1163157884 , overrideEqualsNotHash_2 hashCode : 1956725890,
         * they equals ? true,
         * hashSet : [OverrideEqualsNotHash{age=1, name='1', hashCode='1956725890},
         *            OverrideEqualsNotHash{age=1, name='1', hashCode='1163157884}]
         */

        System.out.println();
        System.out.println("==========================只重写hashCode不重写equals==========================");

        HashSet<OverrideHashNotEquals> overrideHashNotEquals_hashSet = new HashSet<OverrideHashNotEquals>();
        OverrideHashNotEquals overrideHashNotEquals_1 = new OverrideHashNotEquals(1, "1");
        OverrideHashNotEquals overrideHashNotEquals_2 = new OverrideHashNotEquals(1, "1");

        overrideHashNotEquals_hashSet.add(overrideHashNotEquals_1);
        overrideHashNotEquals_hashSet.add(overrideHashNotEquals_2);
        overrideHashNotEquals_hashSet.add(overrideHashNotEquals_1);

        System.out.println("overrideHashNotEquals_1 hashCode : " + overrideHashNotEquals_1.hashCode()
                + " , overrideHashNotEquals_2 hashCode : " + overrideHashNotEquals_2.hashCode()
                + ", they equals ? " + overrideHashNotEquals_1.equals(overrideHashNotEquals_2)
                + ", hashSet : " + overrideHashNotEquals_hashSet
        );
        /* 结果：只是重写hashCode没有重写equals，hashSet底层还是hashMap，putVal会判断hash和equals是否都相同，才认为是同一个元素key
         * overrideHashNotEquals_1 hashCode : 80 , overrideHashNotEquals_2 hashCode : 80,
         * they equals ? false.
         * hashSet : [OverrideHashNotEquals{age=1, name='1', hashCode='80},
         *            OverrideHashNotEquals{age=1, name='1', hashCode='80}]
         */

        System.out.println();
        System.out.println("==========================既重写equals也重写hashCode==========================");

        HashSet<OverrideEqualsAndHash> OverrideEqualsAndHash_hashSet = new HashSet<OverrideEqualsAndHash>();
        OverrideEqualsAndHash overrideEqualsAndHash_1 = new OverrideEqualsAndHash(1, "1");
        OverrideEqualsAndHash overrideEqualsAndHash_2 = new OverrideEqualsAndHash(1, "1");

        OverrideEqualsAndHash_hashSet.add(overrideEqualsAndHash_1);
        OverrideEqualsAndHash_hashSet.add(overrideEqualsAndHash_2);
        OverrideEqualsAndHash_hashSet.add(overrideEqualsAndHash_1);

        System.out.println("overrideEqualsAndHash_1 hashCode : " + overrideEqualsAndHash_1.hashCode()
                + " , overrideEqualsAndHash_2 hashCode : " + overrideEqualsAndHash_2.hashCode()
                + ", they equals ? " + overrideEqualsAndHash_1.equals(overrideEqualsAndHash_2)
                + ", hashSet : " + OverrideEqualsAndHash_hashSet
        );
        /* 结果：既重写equals也重写hashCode，add进去的都当做是相同的元素，所以都是覆盖，hashSet最后最后一个元素
         * overrideEqualsAndHash_1 hashCode : 80 , overrideEqualsAndHash_2 hashCode : 80,
         * they equals ? true,
         * hashSet : [OverrideEqualsAndHash{age=1, name='1', hashCode='80}]
         */

    }


    static class OverrideNeitherEqualsOrHash {
        Integer age;
        String name;

        public OverrideNeitherEqualsOrHash(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "OverrideNeitherEqualsOrHash{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", hashCode='" + hashCode() +
                    '}';
        }
    }

    static class OverrideEqualsNotHash {
        Integer age;
        String name;

        public OverrideEqualsNotHash(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            OverrideEqualsNotHash that = (OverrideEqualsNotHash) o;

            if (age != null ? !age.equals(that.age) : that.age != null) {
                return false;
            }
            return name != null ? name.equals(that.name) : that.name == null;
        }

        @Override
        public String toString() {
            return "OverrideEqualsNotHash{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", hashCode='" + hashCode() +
                    '}';
        }
    }

    static class OverrideHashNotEquals {
        Integer age;
        String name;

        public OverrideHashNotEquals(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public int hashCode() {
            int result = age != null ? age.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "OverrideHashNotEquals{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", hashCode='" + hashCode() +
                    '}';
        }
    }

    static class OverrideEqualsAndHash {
        Integer age;
        String name;

        public OverrideEqualsAndHash(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OverrideEqualsAndHash that = (OverrideEqualsAndHash) o;

            if (age != null ? !age.equals(that.age) : that.age != null) return false;
            return name != null ? name.equals(that.name) : that.name == null;
        }

        @Override
        public int hashCode() {
            int result = age != null ? age.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "OverrideEqualsAndHash{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", hashCode='" + hashCode() +
                    '}';
        }
    }
}
