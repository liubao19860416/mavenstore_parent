package cn.itcast.storemanage.web.aspact;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
/**
 *下面的这些方法名称和规范是按照AspactJ的框架命名来定的,自己也可以随便写;
 *
 * 这里只需要写一个普通的类就行了;不需要实现任何接口或其他;他就是一个切面类;
 * 作用有些像拦截器,监听器,过滤器等的作用感觉类似,
 * 只是这个切面实在spring中使用;
 * 拦截器是在struts2中使用;
 * 过滤器和监听器一般是用在web项目中;
 *
 *
 */
public class MyAspactJAspact {
	
	//添加日志记录所有的方法的执行时间;
	//Logger logger=Logger.getLogger(MyAspactJAspact.class); 
	
	//前置通知2
	public void beforeAdvice2(JoinPoint joinpoint){
		System.out.println("执行的方法内容为:"+joinpoint.toString());
		System.out.println("前置通知2(带参数Joinpoint)执行了!");
	}
	
	//前置通知1
	public void beforeAdvice1(){
		System.out.println("前置通知1(不带参数)执行了!");
	}
	
	//环绕通知(这里是有返回值的!!!,一般只推荐使用这个就行了)
	//注意:这里的异常信息一定要抛出去!!!
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("环绕通知(带1个参数ProceedingJoinPoint)执行了!");
		System.out.println("环绕通知执行前---");
		Object result = proceedingJoinPoint.proceed();
		System.out.println("环绕通知执行后---"+result);
		return result;
	}
	
	//后置通知(基本不用)
		public Object afterReturning(JoinPoint joinpoint,Object result){
			System.out.println("后置通知(带2个参数Joinpoint和Object)执行了!");
			System.out.println(joinpoint+":::::"+result);
//			System.out.println(joinpoint.getClass().getName());
//			System.out.println(result.getClass().getName());
			return result;
		}
		
		
	//异常通知
	public void afterThrowing(JoinPoint joinPoint, Throwable ex) throws Throwable{
		System.out.println("异常通知执行了["+joinPoint.toLongString()+"]方法出现异常：" + ex.getMessage());
		System.out.println();
	}

	//最终通知
	public void after(JoinPoint joinPoint) throws Throwable{
		System.out.println("最终通知执行了---"+joinPoint.toLongString());
	}
	
}
