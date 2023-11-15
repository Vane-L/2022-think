// 开闭原则，通过增加接口实现
type TradeProcessor interface {
    Process(trade *Trade) error
}

type FutureTradeProcessor struct {}

func (ftp *FutureTradeProcessor) Process(trade *Trade) error {
    // process future trade
    return nil
}

type OptionTradeProcessor struct {}

func (otp *OptionTradeProcessor) Process(trade *Trade) error {
    // process option trade
    return nil
}

// 所有引用父类的地方必须能透明地使用其子类的对象。
type Trade interface {
    Process() error
}

type FutureTrade struct {
    Trade
}

func (ft *FutureTrade) Process() error {
    // process future trade
    return nil
}

// 接口隔离原则
type Trade interface {
    Process() error
}

type OptionTrade interface {
    CalculateImpliedVolatility() error
}

type FutureTrade struct {
    Trade
}

func (ft *FutureTrade) Process() error {
    // process future trade
    return nil
}

type OptionTrade struct {
    Trade
}

func (ot *OptionTrade) Process() error {
    // process option trade
    return nil
}

func (ot *OptionTrade) CalculateImpliedVolatility() error {
    // calculate implied volatility
    return nil
}

// 依赖倒置原则：依赖接口不依赖实例
type TradeService interface {
    Save(trade *Trade) error
}

type TradeProcessor struct {
    tradeService TradeService
}

// TradeProcessor结构体可以根据实际需要指定我们存储的数据库类型。
func (tp *TradeProcessor) Process(trade *Trade) error {
    err := tp.tradeService.Save(trade)
    if err != nil {
        return err
    }
    // process trade
    return nil
}

type SqlServerTradeRepository struct {
    db *sql.DB
}

func (str *SqlServerTradeRepository) Save(trade *Trade) error {
    _, err := str.db.Exec("INSERT INTO trades (trade_id, symbol, quantity, price) VALUES (?, ?, ?, ?)", trade.TradeID, trade.Symbol, trade.Quantity, trade.Price)
    if err != nil {
        return err
    }
    return nil
}

type MongoDbTradeRepository struct {
    session *mgo.Session
}

func (mdtr *MongoDbTradeRepository) Save(trade *Trade) error {
    collection := mdtr.session.DB("trades").C("trade")
    err := collection.Insert(trade)
    if err != nil {
        return err
    }
    return nil
}
