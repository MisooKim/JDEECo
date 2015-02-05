package cz.cuni.mff.d3s.jdeeco.network.marshaller;

import java.io.IOException;
import java.util.*;

import cz.cuni.mff.d3s.jdeeco.network.PacketTypes.PacketType;
import cz.cuni.mff.d3s.jdeeco.network.exceptions.MarshallingException;
import cz.cuni.mff.d3s.jdeeco.network.exceptions.UnregistredPacketType;

public class MarshallerRegistry {
	Map<PacketType, Marshaller> marshallers = new HashMap<PacketType, Marshaller>();
	Map<Integer, PacketType> packetTypes = new HashMap<Integer, PacketType>();
	
	public byte[] marshall(PacketType type, Object data) throws IOException, UnregistredPacketType {
		Marshaller marshaller = marshallers.get(type);
		if(marshaller == null) {
			throw new UnregistredPacketType(type);
		}
		try {
			return marshaller.marshall(data);
		} catch(Exception e) {
			throw new MarshallingException(e);
		}
	}
	
	public Object unmarshall(int type, byte[] data) throws ClassNotFoundException, IOException, UnregistredPacketType {
		return unmarshall(resolvePacketType(type), data);
	}
	
	public Object unmarshall(PacketType type, byte[] data) throws ClassNotFoundException, IOException, UnregistredPacketType {
		Marshaller marshaller = marshallers.get(type);
		if(marshaller == null) {
			throw new UnregistredPacketType(type);
		}
		try {
			return marshaller.unmashall(data);
		} catch(Exception e) {
			throw new MarshallingException(e);
		}
	}
	
	public PacketType resolvePacketType(final Integer value) throws UnregistredPacketType {
		PacketType type = packetTypes.get(value);
		
		if(type == null) {
			throw new UnregistredPacketType(value);
		}
		
		return type;
	}
	
	public void registerMarshaller(PacketType type, Marshaller marshaller) {
		marshallers.put(type, marshaller);
		packetTypes.put(type.value(), type);
	}
}
