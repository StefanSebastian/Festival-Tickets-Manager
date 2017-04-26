package protobuf;

import Domain.User;

/**
 * Created by Sebi on 25-Apr-17.
 */
public class ProtoUtils {
    public static FestivalProtobufs.FestivalRequest createLoginRequest(User user){
        FestivalProtobufs.User userDto =
                FestivalProtobufs.User.newBuilder()
                        .setId(user.getUsername())
                        .setPassword(user.getPassword())
                        .build();
        FestivalProtobufs.FestivalRequest request =
                FestivalProtobufs.FestivalRequest.newBuilder()
                        .setType(FestivalProtobufs.FestivalRequest.Type.Login)
                        .setUser(userDto)
                        .build();
        return request;
    }

    public static FestivalProtobufs.FestivalRequest createLogoutRequest(User user){
        FestivalProtobufs.User userDto =
                FestivalProtobufs.User.newBuilder()
                        .setId(user.getUsername())
                        .setPassword(user.getPassword())
                        .build();
        FestivalProtobufs.FestivalRequest request =
                FestivalProtobufs.FestivalRequest.newBuilder()
                        .setType(FestivalProtobufs.FestivalRequest.Type.Logout)
                        .setUser(userDto)
                        .build();
        return request;
    }

    public static User getUser(FestivalProtobufs.FestivalRequest request){
        return new User(request.getUser().getId(), request.getUser().getPassword());
    }

    public static FestivalProtobufs.FestivalResponse createOKResponse(){
        FestivalProtobufs.FestivalResponse response =
                FestivalProtobufs.FestivalResponse.newBuilder()
                        .setType(FestivalProtobufs.FestivalResponse.Type.Ok)
                        .build();
        return response;
    }

    public static FestivalProtobufs.FestivalResponse createErrorResponse(String msg){
        FestivalProtobufs.FestivalResponse response =
                FestivalProtobufs.FestivalResponse.newBuilder()
                .setType(FestivalProtobufs.FestivalResponse.Type.Error)
                .setError(msg)
                .build();
        return response;
    }
}
