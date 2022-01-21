package com.orion.communication.waitAndNotify;

import com.orion.SmallTool;

/**
 * 生产者 和 消费者 ，等到生产固定数量再通知消费者去消费
 * notify 和 wait
 *
 * @author Administrator
 */
public class ProducerAndConsumerFixCnt {
    private static final int MAX_No = 5;

    private static final Object lock = new Object();

    public static void main(String[] args) {
        Product product = new Product();
        Producer p = new Producer("producer", product);
        Consumer c = new Consumer("consumer", product);
        p.start();
        c.start();
    }

    /**
     * 运行结果:
     1642589385536	|	13	|	producer	|	正在生产产品，No ：1
     1642589385536	|	13	|	producer	|	正在生产产品，No ：2
     1642589385536	|	13	|	producer	|	正在生产产品，No ：3
     1642589385536	|	13	|	producer	|	正在生产产品，No ：4
     1642589385536	|	13	|	producer	|	正在生产产品，No ：5
     1642589385536	|	13	|	producer	|	生产已满 5 个，暂停生产，执行wait，让出锁
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：5
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：4
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：3
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：2
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：1
     1642589385536	|	14	|	consumer	|	已消费完 5 个，暂停消费
     1642589385536	|	13	|	producer	|	正在生产产品，No ：1
     1642589385536	|	13	|	producer	|	正在生产产品，No ：2
     1642589385536	|	13	|	producer	|	正在生产产品，No ：3
     1642589385536	|	13	|	producer	|	正在生产产品，No ：4
     1642589385536	|	13	|	producer	|	正在生产产品，No ：5
     1642589385536	|	13	|	producer	|	生产已满 5 个，暂停生产，执行wait，让出锁
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：5
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：4
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：3
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：2
     1642589385536	|	14	|	consumer	|	正在消费产品，No ：1
     1642589385536	|	14	|	consumer	|	已消费完 5 个，暂停消费
     ...略
     */

    static class Product{
        private int count = 0;

        public void increaseProduct(){
            count++;
        }

        public void decreaseProduct(){
            count--;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    static class Producer extends Thread {
        private Product no;

        public Producer(String name, Product no) {
            super(name);
            this.no = no;
        }

        public void incr() {
            synchronized (lock) {
                while (true) {
                    if (no.getCount() == MAX_No) {
                        SmallTool.printTimeAndThread("生产已满 " + no.getCount() + " 个，暂停生产，执行wait，让出锁");
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            SmallTool.printTimeAndThread("wait 被打断：" + e.getMessage());
                        }
                    } else {
                        no.increaseProduct();
                        SmallTool.printTimeAndThread("正在生产产品，No ：" +  no.getCount());
                    }
                }
            }
        }

        @Override
        public void run() {
            incr();
        }
    }

    static class Consumer extends Thread {
        private Product no;

        public Consumer(String name, Product no) {
            super(name);
            this.no = no;
        }

        @Override
        public void run() {
            decr();
        }

        public void decr() {
            synchronized (lock) {
                while (true) {
                    if (no.getCount() == 0) {
                        SmallTool.printTimeAndThread("已消费完 " + MAX_No + " 个，暂停消费");
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            SmallTool.printTimeAndThread("wait 被打断：" + e.getMessage());
                        }
                    } else {
                        SmallTool.printTimeAndThread("正在消费产品，No ：" + no.getCount());
                        no.decreaseProduct();
                    }
                }
            }
        }

    }
}
