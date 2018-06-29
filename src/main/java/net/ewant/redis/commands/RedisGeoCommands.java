package net.ewant.redis.commands;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.List;
import java.util.Map;

public interface RedisGeoCommands {

	  Long geoadd(String key, double longitude, double latitude, String member);

	  Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap);

	  Double geodist(String key, String member1, String member2);

	  Double geodist(String key, String member1, String member2, GeoUnit unit);

	  List<String> geohash(String key, String... members);

	  List<GeoCoordinate> geopos(String key, String... members);

	  List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
                                        GeoUnit unit);

	  List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius,
                                        GeoUnit unit, GeoRadiusParam param);

	  List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit);

	  List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
                                                GeoRadiusParam param);
}
