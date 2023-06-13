package edu.swu.cs.Constants;

public class SystemConstants
{

    //权限菜单中menutype为C
    public static final String MENU = "C";
    //权限菜单中menutype为F
    public static final String BUTTON = "F";
    //权限菜单中status为正常
    public static final int STATUS_NORMAL = 0;

    //订单交换机
    public static final String ORDER_EXCHANGE = "order-event-exchange";
    //判断订单的状态来是否解锁库存的queue
    public static final String ORDER_RELEASE_STOCK_QUEUE = "order.release.stock.queue";
    //订单的延迟队列

    public static final String ORDER_DELAY_QUEUE = "order.delay.queue";

    //高并发订单的延迟队列
    public static final String HOTORDER_DELAY_QUEUE = "hotOrder.delay.queue";
    //高并发的订单处理（保存订单）的队列
    public static final String HOTORDER_QUEUE = "order_hotOrder";


    //解锁库存的队列
    public static final String RELEASE_STOCK_QUEUE = "stock.release.stock.queue";




}