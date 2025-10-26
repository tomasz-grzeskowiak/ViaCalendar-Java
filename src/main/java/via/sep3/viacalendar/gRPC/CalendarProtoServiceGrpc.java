package via.sep3.viacalendar.gRPC;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: Calendar.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CalendarProtoServiceGrpc {

  private CalendarProtoServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "via.sep3.viacalendar.gRPC.CalendarProtoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<via.sep3.viacalendar.gRPC.Calendar.RequestProto,
      via.sep3.viacalendar.gRPC.Calendar.ResponseProto> getSendRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendRequest",
      requestType = via.sep3.viacalendar.gRPC.Calendar.RequestProto.class,
      responseType = via.sep3.viacalendar.gRPC.Calendar.ResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<via.sep3.viacalendar.gRPC.Calendar.RequestProto,
      via.sep3.viacalendar.gRPC.Calendar.ResponseProto> getSendRequestMethod() {
    io.grpc.MethodDescriptor<via.sep3.viacalendar.gRPC.Calendar.RequestProto, via.sep3.viacalendar.gRPC.Calendar.ResponseProto> getSendRequestMethod;
    if ((getSendRequestMethod = CalendarProtoServiceGrpc.getSendRequestMethod) == null) {
      synchronized (CalendarProtoServiceGrpc.class) {
        if ((getSendRequestMethod = CalendarProtoServiceGrpc.getSendRequestMethod) == null) {
          CalendarProtoServiceGrpc.getSendRequestMethod = getSendRequestMethod =
              io.grpc.MethodDescriptor.<via.sep3.viacalendar.gRPC.Calendar.RequestProto, via.sep3.viacalendar.gRPC.Calendar.ResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.sep3.viacalendar.gRPC.Calendar.RequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  via.sep3.viacalendar.gRPC.Calendar.ResponseProto.getDefaultInstance()))
              .setSchemaDescriptor(new CalendarProtoServiceMethodDescriptorSupplier("SendRequest"))
              .build();
        }
      }
    }
    return getSendRequestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CalendarProtoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalendarProtoServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalendarProtoServiceStub>() {
        @java.lang.Override
        public CalendarProtoServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalendarProtoServiceStub(channel, callOptions);
        }
      };
    return CalendarProtoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CalendarProtoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalendarProtoServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalendarProtoServiceBlockingStub>() {
        @java.lang.Override
        public CalendarProtoServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalendarProtoServiceBlockingStub(channel, callOptions);
        }
      };
    return CalendarProtoServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CalendarProtoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CalendarProtoServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CalendarProtoServiceFutureStub>() {
        @java.lang.Override
        public CalendarProtoServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CalendarProtoServiceFutureStub(channel, callOptions);
        }
      };
    return CalendarProtoServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void sendRequest(via.sep3.viacalendar.gRPC.Calendar.RequestProto request,
        io.grpc.stub.StreamObserver<via.sep3.viacalendar.gRPC.Calendar.ResponseProto> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendRequestMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CalendarProtoService.
   */
  public static abstract class CalendarProtoServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CalendarProtoServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CalendarProtoService.
   */
  public static final class CalendarProtoServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CalendarProtoServiceStub> {
    private CalendarProtoServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalendarProtoServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalendarProtoServiceStub(channel, callOptions);
    }

    /**
     */
    public void sendRequest(via.sep3.viacalendar.gRPC.Calendar.RequestProto request,
        io.grpc.stub.StreamObserver<via.sep3.viacalendar.gRPC.Calendar.ResponseProto> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CalendarProtoService.
   */
  public static final class CalendarProtoServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CalendarProtoServiceBlockingStub> {
    private CalendarProtoServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalendarProtoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalendarProtoServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public via.sep3.viacalendar.gRPC.Calendar.ResponseProto sendRequest(via.sep3.viacalendar.gRPC.Calendar.RequestProto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendRequestMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CalendarProtoService.
   */
  public static final class CalendarProtoServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CalendarProtoServiceFutureStub> {
    private CalendarProtoServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CalendarProtoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CalendarProtoServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<via.sep3.viacalendar.gRPC.Calendar.ResponseProto> sendRequest(
        via.sep3.viacalendar.gRPC.Calendar.RequestProto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendRequestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_REQUEST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_REQUEST:
          serviceImpl.sendRequest((via.sep3.viacalendar.gRPC.Calendar.RequestProto) request,
              (io.grpc.stub.StreamObserver<via.sep3.viacalendar.gRPC.Calendar.ResponseProto>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSendRequestMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              via.sep3.viacalendar.gRPC.Calendar.RequestProto,
              via.sep3.viacalendar.gRPC.Calendar.ResponseProto>(
                service, METHODID_SEND_REQUEST)))
        .build();
  }

  private static abstract class CalendarProtoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CalendarProtoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return via.sep3.viacalendar.gRPC.Calendar.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CalendarProtoService");
    }
  }

  private static final class CalendarProtoServiceFileDescriptorSupplier
      extends CalendarProtoServiceBaseDescriptorSupplier {
    CalendarProtoServiceFileDescriptorSupplier() {}
  }

  private static final class CalendarProtoServiceMethodDescriptorSupplier
      extends CalendarProtoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CalendarProtoServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CalendarProtoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CalendarProtoServiceFileDescriptorSupplier())
              .addMethod(getSendRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
