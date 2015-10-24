package cn.itcast.storemanage.web.aspact;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * 这个是传统的AOP编程的环绕通知; 只能是一个通知对应一个切点表达式的形式,扩展后为AspactJ的形式;见MyAspact类;
 * 
 * 程序测试OK!
 */
public class MyMethodAspact implements MethodInterceptor {
	
	//添加日志记录所有的方法的执行时间;
		Logger logger=Logger.getLogger(MyMethodAspact.class); 

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		System.out.println("环绕增强前执行(传统方式)---");
		long time1 = System.nanoTime();// 纳秒值
		Object result = methodInvocation.proceed();
		long time2 = System.nanoTime();// 纳秒值
		System.out.println("环绕增强后执行(传统方式)---");

		String methodName = methodInvocation.getMethod().getName();
		// System.out.println("当前方法名为:"+methodName);
		// 结果是:findHistoriesByPage

		String className = methodInvocation.getThis().getClass()
				.getSimpleName();
		//System.out.println("父类类名为:" + className);
		// 结果是:HistoryDAOImpl

		// className = methodInvocation.getThis().getClass().getName();
		// System.out.println("父类方法名全路径为:"+className);
		// 结果是:cn.itcast.storemanage.dao.impl.HistoryDAOImpl

		// className = methodInvocation.getThis().getClass().getCanonicalName();
		// System.out.println("父类类名全路径为:"+className);
		// 结果是:cn.itcast.storemanage.dao.impl.HistoryDAOImpl
		
		//1秒=1000毫秒=1000*1000*1000纳秒;
		System.out.println("(传统方式)名称为{"+className+"}的类,执行了方法["+methodName+"],所用时间为:" + (time2 - time1) / 1000.0/1000.0+ "毫秒");
		String messgae="(传统方式)名称为{"+className+"}的类,执行了方法["+methodName+"],所用时间为:" + (time2 - time1) / 1000.0/1000.0+ "毫秒,执行时间为:"+new Date().toLocaleString();
		
		//自己写的一个简单的日至格式;提高输出的级别,方便查看日志;
		logger.fatal(messgae);
		return result;
	}
	
	/*
	 * //前置通知1 public void beforeAdvice1(){
	 * System.out.println("前置通知1(不带参数)执行了!"); } //前置通知2 public void
	 * beforeAdvice2(Joinpoint joinpoint){
	 * System.out.println(joinpoint.toString());
	 * System.out.println("前置通知2(带参数Joinpoint)执行了!"); }
	 * 
	 * //环绕通知 //注意:这里的异常信息一定要抛出去!!! public Object around(ProceedingJoinPoint
	 * proceedingJoinPoint) throws Throwable{
	 * System.out.println("环绕通知(带1个参数ProceedingJoinPoint)执行了!");
	 * System.out.println("环绕通知执行前---"); Object result =
	 * proceedingJoinPoint.proceed(); System.out.println("环绕通知执行后---"+result);
	 * return result; }
	 * 
	 * //后置通知 public void afterReturning(Joinpoint joinpoint,Object result){
	 * System.out.println("后置通知(带2个参数Joinpoint和Object)执行了!");
	 * System.out.println(joinpoint.toString()+":::::"+result.toString());
	 * System.out.println(joinpoint.getClass().getName());
	 * System.out.println(result.getClass().getName()); }
	 * 
	 * 
	 * //异常通知 public Object afterThrowing(ProceedingJoinPoint
	 * proceedingJoinPoint) throws Throwable{
	 * System.out.println("异常通知(带1个参数ProceedingJoinPoint)执行了!");
	 * System.out.println("异常通知执行前---"); Object result =
	 * proceedingJoinPoint.proceed(); System.out.println("异常通知执行后---"+result);
	 * return result; }
	 * 
	 * //最终通知 public Object after(ProceedingJoinPoint proceedingJoinPoint)
	 * throws Throwable{
	 * System.out.println("最终通知(带1个参数ProceedingJoinPoint)执行了!");
	 * System.out.println("最终通知执行前---"); Object result =
	 * proceedingJoinPoint.proceed(); System.out.println("最终通知执行后---"+result);
	 * return result; }
	 */

}
