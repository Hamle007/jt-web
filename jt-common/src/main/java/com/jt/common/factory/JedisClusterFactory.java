package com.jt.common.factory;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster>{

	private JedisPoolConfig poolConfig;
	private Resource addressConfig;
	private String perKey;
	
	@Override
	public JedisCluster getObject() throws Exception {
		Set<HostAndPort> nodes = getNodes();
		JedisCluster jedisCluster = new JedisCluster(nodes, poolConfig);
		return jedisCluster;
	}
	
	private Set<HostAndPort> getNodes() {
		Set<HostAndPort> nodes = new HashSet<>();
		Properties properties = new Properties();
		try {
			properties.load(addressConfig.getInputStream());
			for(Object node : properties.keySet()) {
				String strNode = (String)node;
				if(strNode.startsWith(perKey)) {
					String value = properties.getProperty(strNode);
					String [] argus = value.split(":");
					nodes.add(new HostAndPort(argus[0], Integer.parseInt(argus[1])));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}	
		return nodes;
	}

	@Override
	public Class<JedisCluster> getObjectType() {
		return JedisCluster.class;
	}
	
	@Override
	public boolean isSingleton() {
		return false;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	
	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public Resource getAddressConfig() {
		return addressConfig;
	}

	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}

	public String getPerKey() {
		return perKey;
	}

	public void setPerKey(String perKey) {
		this.perKey = perKey;
	}
	
	
	
	
	
	
}
