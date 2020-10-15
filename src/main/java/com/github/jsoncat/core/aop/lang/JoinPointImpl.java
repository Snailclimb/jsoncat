package com.github.jsoncat.core.aop.lang;

public class JoinPointImpl implements JoinPoint {

    private final Object adviceBean;
    private final Object target;
    private Object[] args;

    public JoinPointImpl(Object adviceBean, Object target, Object[] args) {
        this.adviceBean = adviceBean;
        this.target = target;
        this.args = args;
    }

    @Override
    public Object getAdviceBean() {
        return adviceBean;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Object[] getArgs() {
        if (args == null) {
            args = new Object[0];
        }
        Object[] argsCopy = new Object[args.length];
        System.arraycopy(args, 0, argsCopy, 0, args.length);
        return argsCopy;
    }
}
