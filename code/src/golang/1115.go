// 单例模式
var once sync.Once
var instance interface{}
func GetInstance() *singleton {
  once.Do(func() {
    instance = &amp;amp;singleton{}
  })
  return instance
}

// 工厂模式
type simpleInterest struct {
  principal      int
  rateOfInterest int
  time           int
}

type compoundInterest struct {
  principal      int
  rateOfInterest int
  time           int
}

// Interface
type InterestCalculator interface {
  Calculate()
}

func (si *simpleInterest) Calculate() {
  // logic to calculate simple interest
}

func (co *compoundInterest) Calculate() {
  // logic to calculate compound interest
}

func NewCalculator(kind string) InterestCalculator {
  if kind == "simple" {
    return simpleInterest{}
  }
  return compoundInterest{}
}

func Factory_Interface() {
  siCalculator := NewCalculator("simple")
  siCalculator.Calculate() // Invokes simple interest calculation logic
  ciCalculator := NewCalculator("compound")
  ciCalculator.Calculate() // Invokes compound interest calculation logic
}

// 代理模式
type zkClient struct {
  ServiceName string
  Client      client.Client
  opts        []client.Option
}

// NewClientProxy create new zookeeper backend request proxy, required parameter zookeeper name service: trpc.zookeeper.xxx.xxx.
func NewClientProxy(name string, opts ...client.Option) Client {
  c := &amp;amp;zkClient{
    ServiceName: name,
    Client:      client.DefaultClient,
    opts:        opts,
  }
  c.opts = append(c.opts, client.WithProtocol("zookeeper"), client.WithDisableServiceRouter())
  return c
}

// Get execute zookeeper get command.
func (c *zkClient) Get(ctx context.Context, path string) ([]byte, *zk.Stat, error) {
  req := &amp;amp;Request{
    Path: path,
    Op:   OpGet{},
  }
  rsp := &amp;amp;Response{}
  ctx, msg := codec.WithCloneMessage(ctx)
  defer codec.PutBackMessage(msg)
  msg.WithClientRPCName(fmt.Sprintf("/%s/Get", c.ServiceName))
  msg.WithCalleeServiceName(c.ServiceName)
  msg.WithSerializationType(-1) // non-serialization
  msg.WithClientReqHead(req)
  msg.WithClientRspHead(rsp)
  if err := c.Client.Invoke(ctx, req, rsp, c.opts...); err != nil {
    return nil, nil, err
  }
  return rsp.Data, rsp.Stat, nil
}


// 观察者模式
type Item struct {
    observerList []Observer
    name         string
    inStock      bool
}

func newItem(name string) *Item {
    return &Item{
        name: name,
    }
}
func (i *Item) updateAvailability() {
    fmt.Printf("Item %s is now in stock\n", i.name)
    i.inStock = true
    i.notifyAll()
}

func (i *Item) register(o Observer) {
    i.observerList = append(i.observerList, o)
}

func (i *Item) notifyAll() {
    for _, observer := range i.observerList {
        observer.update(i.name)
    }
}

