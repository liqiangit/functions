package com.liqiangit.jmeter.functions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.bouncycastle.util.encoders.Base64;
//https://www.cnblogs.com/qiaoyeye/p/7218770.html
public class File2Base64 extends AbstractFunction {

	// 自定义function的描述
	private static final List<String> desc = new LinkedList<String>();
	static {
		desc.add("图片路径");
	}
	static {
		desc.add("图片base64后存放变量");
	}
	private static final String KEY = "__File2Base64";

	// 存放传入参数的值的变量
	private Object[] values;

	// 描述参数
	public List<String> getArgumentDesc() {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	// 函数的执行
	public synchronized String execute(SampleResult arg0, Sampler arg1) throws InvalidVariableException {
		// TODO Auto-generated method stub
		JMeterVariables localJMeterVariables = getVariables();
		String str1 = ((CompoundVariable) this.values[0]).execute();

		String str2 = getImgBase64(str1);

		if ((localJMeterVariables != null) && (this.values.length > 1)) {
			String str3 = ((CompoundVariable) this.values[1]).execute().trim();
			localJMeterVariables.put(str3, str2);
		}

		return str2;
	}

	@Override
	public String getReferenceKey() {
		// TODO Auto-generated method stub
		// 提供jmeter函数助手显示的名称
		return KEY;
	}

	@Override
	public synchronized void setParameters(Collection<CompoundVariable> arg0) throws InvalidVariableException {
		// TODO Auto-generated method stub
		// 检查参数的个数，支持的方法有2个，具体用法参加api：
		/**
		 * protected void checkParameterCount(Collection<CompoundVariable> parameters,
		 * int count) throws InvalidVariableException Utility method to check parameter
		 * counts. Parameters: parameters - collection of parameters count - number of
		 * parameters expected
		 */
		// -----------------
		/**
		 * 
		 * protected void checkParameterCount(Collection<CompoundVariable> parameters,
		 * int min, int max) throws InvalidVariableException Utility method to check
		 * parameter counts. Parameters: parameters - collection of parameters min -
		 * minimum number of parameters allowed max - maximum number of parameters
		 * allowed
		 */
		// checkParameterCount(arg0, 1);
		checkParameterCount(arg0, 1, 2);
		// 将参数值存入变量中
		this.values = arg0.toArray();

	}

	public String getImgBase64(String filePath) {
		InputStream in = null;
		byte[] data = null;
		String result = null;
		try {
			in = new FileInputStream(filePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();

			Base64 encoder = new Base64();
			result = encoder.toBase64String(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println(new File2Base64().getImgBase64("E:/现场图片1.jpg"));
	}
}