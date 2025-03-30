package com.project.utils;
import com.project.dto.PointDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtil {
    public static Point createPoint(PointDto pointDto) {

        if (pointDto == null || pointDto.getCoordinates() == null || pointDto.getCoordinates().length < 2) {
            throw new IllegalArgumentException("Invalid coordinates provided");
        }

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(pointDto.getCoordinates()[0],
                pointDto.getCoordinates()[1]
        );
        return geometryFactory.createPoint(coordinate);
    }
}
