package com.orion.communication.lockCondition;

import com.orion.SmallTool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者与消费者，采用wait、notify形式，生产一个消费一个
 *
 * @author Administrator
 * @date 2022/1/19
 */
public class ProducerAndConsumerOneByOne {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        Product product = new Product();
        Producer producer = new Producer(product, "p");
        Consumer consumer = new Consumer(product, "c");
        producer.start();
        consumer.start();
    }

    /**
     * 运行结果：
     * 1642589328461	|	13	|	p	|	生产一个商品*****
     * 1642589328461	|	14	|	c	|	消费一个商品#####
     * 1642589328461	|	13	|	p	|	生产一个商品*****
     * 1642589328461	|	14	|	c	|	消费一个商品#####
     * 1642589328461	|	13	|	p	|	生产一个商品*****
     * 1642589328461	|	14	|	c	|	消费一个商品#####
     * 1642589328461	|	13	|	p	|	生产一个商品*****
     * 1642589328461	|	14	|	c	|	消费一个商品#####
     * 1642589328461	|	13	|	p	|	生产一个商品*****
     * 1642589328461	|	14	|	c	|	消费一个商品#####
     * 1642589328461	|	13	|	p	|	生产一个商品*****
     * 1642589328461	|	14	|	c	|	消费一个商品#####
     * ...略
     */


    static class Product {
        private int count = 0;

        public void increaseProduct() {
            count++;
        }

        public void decreaseProduct() {
            count--;
        }

        public int getCount() {
            return count;
        }
    }

    static class Producer extends Thread {

        private Product product;

        public Producer(Product product, String name) {
            super(name);
            this.product = product;
        }

        @Override
        public void run() {
            while (product.getCount() == 0) {
                lock.lock();
                product.increaseProduct();
                SmallTool.printTimeAndThread("生产一个商品*****");
                condition.signal();
                try {
                    //SmallTool.printTimeAndThread("让出锁*****");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Consumer extends Thread {
        private Product product;

        public Consumer(Product product, String name) {
            super(name);
            this.product = product;
        }

        @Override
        public void run() {
            while (product.getCount() != 0) {
                lock.lock();
                product.decreaseProduct();
                SmallTool.printTimeAndThread("消费一个商品#####");
                condition.signal();
                try {
                    //SmallTool.printTimeAndThread("让出锁#####");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}


