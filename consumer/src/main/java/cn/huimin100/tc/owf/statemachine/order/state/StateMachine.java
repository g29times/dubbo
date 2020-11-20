package cn.huimin100.tc.owf.statemachine.order.state;

import cn.huimin100.tc.owf.statemachine.Demo;
import cn.huimin100.tc.owf.statemachine.order.StateRequest;
import cn.huimin100.tc.owf.statemachine.order.domain.Order;
import cn.huimin100.tc.owf.statemachine.order.state.enums.LogisticsStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.OrderStatusEnum;
import cn.huimin100.tc.owf.statemachine.order.state.enums.PayStatusEnum;

import java.util.List;
import java.util.Map;

/**
 * . _________         .__   _____   __
 * ./   _____/__  _  __|__|_/ ____\_/  |_
 * .\_____  \ \ \/ \/ /|  |\   __\ \   __\
 * ./        \ \     / |  | |  |    |  |
 * /_______  /  \/\_/  |__| |__|    |__|
 * .       \/
 * <p>
 * <a href="www.google.com">google</a>
 *
 * @author li tong
 * @description: 状态机实例
 * @date 2020/11/17 14:54
 * @see Object
 * @since 1.0
 */
@Demo
public class StateMachine {

    private int businessLine;

    private List<OrderStateRequest> stateNodes;

    // 1 初始化节点 2 接入方如何使用/实现 3 节点增删
    void init() {
    }

    /**
     * 核心 规则编排查找器
     * TODO 策略
     * 1,1,1 -> OrderCreate
     *
     * @return 状态执行器
     */
    public OrderStateRequest get(Map<Integer, StateRequest<Order>> typeState) {
        if (typeState.get(1).getStateValue() == OrderStatusEnum.CREATE.getCode()
                && typeState.get(2) == null && typeState.get(3)== null) {
            return OrderStatusEnum.CREATE.getState();
        } else if (typeState.get(1).getStateValue() == OrderStatusEnum.CREATE.getCode()
                && typeState.get(2).getStateValue() == PayStatusEnum.WAITING.getCode() && typeState.get(3)== null) {
            return PayStatusEnum.WAITING.getState();
        } else if (typeState.get(1).getStateValue() == OrderStatusEnum.CREATE.getCode()
                && typeState.get(2).getStateValue() == PayStatusEnum.WAITING.getCode()
                && typeState.get(3).getStateValue() == LogisticsStatusEnum.PICK.getCode()) {
            return LogisticsStatusEnum.PICK.getState();
        } else if (typeState.get(1).getStateValue() == OrderStatusEnum.FINISH.getCode()
                && typeState.get(2).getStateValue() == PayStatusEnum.WAITING.getCode()
                && typeState.get(3).getStateValue() == LogisticsStatusEnum.PICK.getCode()) {
            return OrderStatusEnum.FINISH.getState();
        } else {
            return OrderStatusEnum.CANCEL.getState();
        }
    }

}
