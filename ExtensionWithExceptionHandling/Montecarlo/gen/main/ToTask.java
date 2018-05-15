

package main;


public class ToTask implements java.io.Serializable {
    private java.lang.String header;

    private long randomSeed;

    public ToTask(java.lang.String header, long randomSeed) {
        main.ToTask.this.header = header;
        main.ToTask.this.randomSeed = randomSeed;
    }

    public java.lang.String get_header() {
        return main.ToTask.this.header;
    }

    public void set_header(java.lang.String header) {
        main.ToTask.this.header = header;
    }

    public long get_randomSeed() {
        return main.ToTask.this.randomSeed;
    }

    public void set_randomSeed(long randomSeed) {
        main.ToTask.this.randomSeed = randomSeed;
    }
}

