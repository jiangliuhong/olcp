package top.jiangliuhong.olcp.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DataSourceAop {

    @Pointcut("@annotation(top.jiangliuhong.olcp.common.annos.Master) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.insert*(..)) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.save*(..)) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.add*(..)) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.update*(..)) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.edit*(..)) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.delete*(..)) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Pointcut("!@annotation(top.jiangliuhong.olcp.common.annos.Master) " +
            "&& (execution(* top.jiangliuhong.olcp.*.service..*.select*(..)) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.query*(..)) " +
            "|| execution(* top.jiangliuhong.olcp.*.service..*.get*(..)))")
    public void readPointcut() {

    }

    @Before("writePointcut()")
    public void write() {
        ClusterDBContext.master();
    }

    @Before("readPointcut()")
    public void read() {
        ClusterDBContext.slave();
    }
}
